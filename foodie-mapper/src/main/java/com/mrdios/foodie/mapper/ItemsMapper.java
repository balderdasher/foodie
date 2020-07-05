package com.mrdios.foodie.mapper;

import com.mrdios.foodie.pojo.Items;
import com.mrdios.foodie.pojo.vo.SearchItemsVo;
import com.mrdios.foodie.pojo.vo.ShopCartVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ItemsMapper extends MyMapper<Items> {
    /**
     * 按关键字搜索商品
     */
    List<SearchItemsVo> searchItems(@Param("param") Map<String, Object> prams);

    /**
     * 按三级分类搜索商品
     */
    List<SearchItemsVo> searchItemsByThirdCat(@Param("param") Map<String, Object> prams);

    /**
     * 根据规格查询商品信息,用于购物车商品信息刷新
     */
    List<ShopCartVo> queryItemsBySpecIds(@Param("specIds") List<String> specIds);

    /**
     * 库存扣减
     *
     * @param specId   规格id
     * @param buyCount 购买数量
     */
    int decreaseItemSpecStock(@Param("specId") String specId, @Param("buyCount") int buyCount);
}