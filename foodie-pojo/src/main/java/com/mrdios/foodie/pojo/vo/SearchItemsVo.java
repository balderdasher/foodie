package com.mrdios.foodie.pojo.vo;

import lombok.Data;

import java.util.Date;

/**
 * 商品搜索vo
 *
 * @author CodePorter
 * @date 2020-06-21
 */
@Data
public class SearchItemsVo {
    private String itemId;
    private String itemName;
    private String imgUrl;
    private Integer sellCounts;
    private Integer price;  // 为保证精度，以分为单位
}
