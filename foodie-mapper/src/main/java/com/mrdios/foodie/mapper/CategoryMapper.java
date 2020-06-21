package com.mrdios.foodie.mapper;

import com.mrdios.foodie.pojo.Category;
import com.mrdios.foodie.pojo.vo.CategoryVo;
import com.mrdios.foodie.pojo.vo.NewItemVo;

import java.util.List;

public interface CategoryMapper extends MyMapper<Category> {
    /**
     * 获取一级分类的子分类
     *
     * @param rootCatId 一级分类id
     */
    List<CategoryVo> getSubCatList(Integer rootCatId);

    /**
     * 获取一级分类最新6个商品
     *
     * @param rootCatId
     * @return
     */
    List<NewItemVo> get6NewItems(Integer rootCatId);
}