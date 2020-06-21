package com.mrdios.foodie.service;

import com.mrdios.foodie.common.bean.PageModel;
import com.mrdios.foodie.pojo.Items;
import com.mrdios.foodie.pojo.ItemsImg;
import com.mrdios.foodie.pojo.ItemsParam;
import com.mrdios.foodie.pojo.ItemsSpec;
import com.mrdios.foodie.pojo.vo.ItemCommentCountVo;
import com.mrdios.foodie.pojo.vo.ItemCommentVo;

import java.util.List;

/**
 * 商品详情service
 *
 * @author CodePorter
 * @date 2020-06-21
 */
public interface ItemService {

    /**
     * 根据商品id查询商品详情
     *
     * @param itemId 商品id
     * @return
     */
    Items getItemById(String itemId);

    /**
     * 根据商品id查询商品图片
     *
     * @param itemId 商品id
     * @return
     */
    List<ItemsImg> getItemImgs(String itemId);

    /**
     * 根据商品id查询商品规格
     *
     * @param itemId 商品id
     * @return
     */
    List<ItemsSpec> getItemSpecList(String itemId);

    /**
     * 根据商品id查询商品参数
     *
     * @param itemId 商品id
     * @return
     */
    ItemsParam getItemParam(String itemId);

    /**
     * 获取商品评价数量信息
     *
     * @param itemId 商品id
     */
    ItemCommentCountVo getItemCommentCount(String itemId);

    /**
     * 分页查询商品评价
     *
     * @param itemId   商品id
     * @param level    评价登记
     * @param page     页码
     * @param pageSize 每页条数
     */
    PageModel<ItemCommentVo> getItemCommentsPage(String itemId, Integer level, Integer page, Integer pageSize);

}
