package com.mrdios.foodie.pojo.vo;

import lombok.Data;

/**
 * 商品评价数量vo
 *
 * @author CodePorter
 * @date 2020-06-21
 */
@Data
public class ItemCommentCountVo {
    private Integer totalCounts;
    private Integer goodCounts;
    private Integer normalCounts;
    private Integer badCounts;
}
