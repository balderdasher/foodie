package com.mrdios.foodie.pojo.vo;

import lombok.Data;

import java.util.List;

/**
 * 一级分类最新商品vo
 *
 * @author CodePorter
 * @date 2020-06-20
 */
@Data
public class NewItemVo {
    private Integer rootCatId;
    private String rootCatName;
    private String slogan;
    private String rootCatImage;
    private String bgColor;
    private List<NewItemSimpleVo> simpleItemList;
}
