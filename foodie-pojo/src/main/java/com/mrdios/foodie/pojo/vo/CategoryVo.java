package com.mrdios.foodie.pojo.vo;

import lombok.Data;

import java.util.List;

/**
 * 一级分类分类vo
 *
 * @author CodePorter
 * @date 2020-06-20
 */
@Data
public class CategoryVo {
    private Integer id;
    private String name;
    private Integer type;
    private Integer fatherId;
    private List<SubCategoryVo> subCatList;
}
