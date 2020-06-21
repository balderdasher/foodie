package com.mrdios.foodie.pojo.vo;

import com.mrdios.foodie.pojo.Items;
import com.mrdios.foodie.pojo.ItemsImg;
import com.mrdios.foodie.pojo.ItemsParam;
import com.mrdios.foodie.pojo.ItemsSpec;
import lombok.Data;

import java.util.List;

/**
 * 商品信息vo
 *
 * @author CodePorter
 * @date 2020-06-21
 */
@Data
public class ItemInfoVo {
    private Items item;
    private List<ItemsImg> itemImgList;
    private List<ItemsSpec> itemSpecList;
    private ItemsParam itemParams;
}
