package com.mrdios.foodie.pojo.bo;

import lombok.Data;

/**
 * 用于创建订单的 BO
 *
 * @author CodePorter
 * @date 2020-07-04
 */
@Data
public class CreateOrderBo {
    private String userId;
    private String itemSpecIds;
    private String addressId;
    private Integer payMethod;
    private String leftMsg;
}
