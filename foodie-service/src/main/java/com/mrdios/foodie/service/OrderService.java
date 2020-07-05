package com.mrdios.foodie.service;

import com.mrdios.foodie.pojo.OrderStatus;
import com.mrdios.foodie.pojo.bo.CreateOrderBo;
import com.mrdios.foodie.pojo.vo.OrderVO;

/**
 * 订单相关接口
 *
 * @author CodePorter
 * @date 2020-06-13
 */
public interface OrderService {
    /**
     * 创建订单
     *
     * @param createOrderBo 订单信息
     */
    OrderVO createOrder(CreateOrderBo createOrderBo);

    /**
     * 修改订单状态
     *
     * @param orderId     订单id
     * @param orderStatus 订单状态
     */
    void updateOrderStatus(String orderId, Integer orderStatus);

    /**
     * 获取订单状态
     *
     * @param orderId 订单id
     */
    OrderStatus queryOrderStatus(String orderId);

    /**
     * 关闭超时未支付订单
     */
    void closeOrder();
}
