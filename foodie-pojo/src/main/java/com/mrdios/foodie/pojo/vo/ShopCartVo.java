package com.mrdios.foodie.pojo.vo;

import lombok.Data;

/**
 * 购物车参数对象
 *
 * @author CodePorter
 * @date 2020-06-29
 */
@Data
public class ShopCartVo {
    private String itemId;
    private String itemImgUrl;
    private String itemName;
    private String specId;
    private String specName;
    private String priceDiscount;
    private String priceNormal;
}
