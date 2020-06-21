package com.mrdios.foodie.mapper;

import com.mrdios.foodie.pojo.ItemsComments;
import com.mrdios.foodie.pojo.vo.ItemCommentVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ItemsCommentsMapper extends MyMapper<ItemsComments> {
    /**
     * 查询商品评价
     *
     * @param itemId 商品id
     * @param level  评价等级
     */
    List<ItemCommentVo> getItemComments(@Param("itemId") String itemId, @Param("level") Integer level);
}