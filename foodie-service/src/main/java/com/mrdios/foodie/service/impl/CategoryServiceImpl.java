package com.mrdios.foodie.service.impl;

import com.mrdios.foodie.mapper.CategoryMapper;
import com.mrdios.foodie.pojo.Category;
import com.mrdios.foodie.pojo.vo.CategoryVo;
import com.mrdios.foodie.pojo.vo.NewItemVo;
import com.mrdios.foodie.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author CodePorter
 * @date 2020-06-20
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Category> getLevelOneCategories() {
        Example example = new Example(Category.class);
        example.createCriteria().andEqualTo("type", 1);
        return categoryMapper.selectByExample(example);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<CategoryVo> getSubCatList(Integer rootCatId) {
        return categoryMapper.getSubCatList(rootCatId);
    }

    @Override
    public List<NewItemVo> get6NewItemList(Integer rootCatId) {
        return categoryMapper.get6NewItems(rootCatId);
    }
}
