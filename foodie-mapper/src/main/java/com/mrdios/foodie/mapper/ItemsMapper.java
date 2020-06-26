package com.mrdios.foodie.mapper;

import com.mrdios.foodie.pojo.Items;
import com.mrdios.foodie.pojo.vo.SearchItemsVo;
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
}