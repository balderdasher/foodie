package com.mrdios.foodie.pojo.vo;

import lombok.Data;

import java.util.Date;

/**
 * 商品评价vo
 *
 * @author CodePorter
 * @date 2020-06-21
 */
@Data
public class ItemCommentVo {
    private Integer commentLevel;
    private String content;
    private String sepcName;
    private Date createdTime;
    private String userFace;
    private String nickname;
}
