package com.mrdios.foodie.service;

import com.mrdios.foodie.pojo.Category;
import com.mrdios.foodie.pojo.vo.CategoryVo;
import com.mrdios.foodie.pojo.vo.NewItemVo;

import java.util.List;

/**
 * 商品分类接口
 *
 * @author CodePorter
 * @date 2020-06-20
 */
public interface CategoryService {
    /**
     * 获取一级分类
     */
    List<Category> getLevelOneCategories();

    /**
     * 获取一级分类的子分类
     *
     * @param rootCatId 一级分类id
     */
    List<CategoryVo> getSubCatList(Integer rootCatId);

    /**
     * 获取一级分类6个最新商品
     *
     * @param rootCatId 一级分类id
     */
    List<NewItemVo> get6NewItemList(Integer rootCatId);

}
