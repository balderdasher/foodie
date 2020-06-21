package com.mrdios.foodie.pojo.vo;

import lombok.Data;

/**
 * 一级分类子分类vo
 *
 * @author CodePorter
 * @date 2020-06-20
 */
@Data
public class SubCategoryVo {
    private Integer subId;
    private String subName;
    private Integer subType;
    private Integer subFatherId;
}
