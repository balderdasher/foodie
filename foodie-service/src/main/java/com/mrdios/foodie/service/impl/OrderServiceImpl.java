package com.mrdios.foodie.service.impl;

import com.mrdios.foodie.BizException;
import com.mrdios.foodie.common.enums.OrderStatusEnum;
import com.mrdios.foodie.common.enums.YesOrNoEnum;
import com.mrdios.foodie.common.utils.DateUtil;
import com.mrdios.foodie.mapper.OrderItemsMapper;
import com.mrdios.foodie.mapper.OrderStatusMapper;
import com.mrdios.foodie.mapper.OrdersMapper;
import com.mrdios.foodie.pojo.*;
import com.mrdios.foodie.pojo.bo.CreateOrderBo;
import com.mrdios.foodie.pojo.vo.MerchantOrderVo;
import com.mrdios.foodie.pojo.vo.OrderVO;
import com.mrdios.foodie.service.ItemService;
import com.mrdios.foodie.service.OrderService;
import com.mrdios.foodie.service.UserAddressService;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static com.mrdios.foodie.common.constant.Constant.PayConstants.PAY_NOTIFY_URL;

/**
 * @author CodePorter
 * @date 2020-07-04
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private OrderItemsMapper orderItemsMapper;
    @Autowired
    private OrderStatusMapper orderStatusMapper;
    @Autowired
    private UserAddressService userAddressService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private Sid sid;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public OrderVO createOrder(CreateOrderBo createOrderBo) {
        String addressId = createOrderBo.getAddressId();
        String userId = createOrderBo.getUserId();
        UserAddress address = userAddressService.queryUserAddress(userId, addressId);
        if (null == address) {
            throw new BizException("收货地址不存在");
        }
        int postAmount = 0; // 邮费、默认包邮
        // 1. 插入订单主表记录
        Orders order = new Orders();
        order.setId(sid.nextShort());
        order.setLeftMsg(createOrderBo.getLeftMsg());
        order.setPayMethod(createOrderBo.getPayMethod());
        order.setPostAmount(postAmount);
        order.setUserId(userId);
        order.setReceiverAddress(String.join(" ", address.getProvince(),
                address.getCity(), address.getDistrict(), address.getDetail()));
        order.setReceiverMobile(address.getMobile());
        order.setReceiverName(address.getReceiver());
        order.setIsComment(YesOrNoEnum.NO.getCode());
        order.setIsDelete(YesOrNoEnum.NO.getCode());
        order.setCreatedTime(new Date());
        order.setUpdatedTime(new Date());

        // 2. 插入订单明细表记录
        String itemSpecIds = createOrderBo.getItemSpecIds();
        String[] specIds = itemSpecIds.split(",");
        Integer totalAmount = 0; // 商品原价累计
        Integer realPayAmount = 0; // 实际支付累计
        for (String specId : specIds) {
            // 2.1 查询规格相关信息
            ItemsSpec itemsSpec = itemService.queryItemSpecById(specId);
            // 2.2 计算订单主表中的两个价格字段
            int buyCount = 1; // TODO: 2020/7/4 0004 整合redis后从缓存中取购买数量
            totalAmount += itemsSpec.getPriceNormal() * buyCount;
            realPayAmount += itemsSpec.getPriceDiscount() * buyCount;
            String itemId = itemsSpec.getItemId();
            Items item = itemService.getItemById(itemId);
            String imgUrl = itemService.queryItemMainImgById(itemId);
            // 2.3 插入订单明细
            OrderItems items = new OrderItems();
            items.setId(sid.nextShort());
            items.setBuyCounts(buyCount);
            items.setItemId(itemId);
            items.setItemImg(imgUrl);
            items.setItemName(item.getItemName());
            items.setItemSpecId(specId);
            items.setItemSpecName(itemsSpec.getName());
            items.setOrderId(order.getId());
            items.setPrice(itemsSpec.getPriceDiscount());
            orderItemsMapper.insert(items);
            // 2.4 扣除每个规格的库存
            itemService.decreaseItemSpecStock(specId, buyCount);
        }

        order.setTotalAmount(totalAmount);
        order.setRealPayAmount(realPayAmount);
        ordersMapper.insert(order);

        // 3. 插入订单状态表记录
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderId(order.getId());
        orderStatus.setCreatedTime(new Date());
        orderStatus.setOrderStatus(OrderStatusEnum.WAIT_PAY.type);
        orderStatusMapper.insert(orderStatus);

        // 4. 创建传给支付中心的商户订单信息
        MerchantOrderVo merchantOrderVo = new MerchantOrderVo();
        merchantOrderVo.setMerchantUserId(userId);
        merchantOrderVo.setMerchantOrderId(order.getId());
        merchantOrderVo.setAmount(realPayAmount);
        merchantOrderVo.setPayMethod(order.getPayMethod());
        merchantOrderVo.setReturnUrl(PAY_NOTIFY_URL);

        // 5. 创建自定义订单信息
        OrderVO orderVO = new OrderVO();
        orderVO.setOrderId(order.getId());
        orderVO.setMerchantOrderVo(merchantOrderVo);
        return orderVO;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateOrderStatus(String orderId, Integer orderStatus) {
        OrderStatus status = new OrderStatus();
        status.setOrderId(orderId);
        status.setOrderStatus(orderStatus);
        status.setPayTime(new Date());
        orderStatusMapper.updateByPrimaryKeySelective(status);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public OrderStatus queryOrderStatus(String orderId) {
        return orderStatusMapper.selectByPrimaryKey(orderId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void closeOrder() {
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderStatus(OrderStatusEnum.WAIT_DELIVER.type);
        List<OrderStatus> statuses = orderStatusMapper.select(orderStatus);
        statuses.forEach(orderStatus1 -> {
            Date createTime = orderStatus1.getCreatedTime();
            int days = DateUtil.daysBetween(createTime, new Date());
            if (days > 1) {
                doCloseOrder(orderStatus1.getOrderId());
            }
        });
    }

    @Transactional(propagation = Propagation.REQUIRED)
    void doCloseOrder(String orderId) {
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderId(orderId);
        orderStatus.setOrderStatus(OrderStatusEnum.CLOSE.type);
        orderStatus.setCloseTime(new Date());
        orderStatusMapper.updateByPrimaryKeySelective(orderStatus);
    }
}
