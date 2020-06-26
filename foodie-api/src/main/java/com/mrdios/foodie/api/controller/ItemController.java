package com.mrdios.foodie.api.controller;

import com.mrdios.foodie.common.bean.ApiResponse;
import com.mrdios.foodie.common.bean.PageModel;
import com.mrdios.foodie.common.constant.Constant;
import com.mrdios.foodie.pojo.Items;
import com.mrdios.foodie.pojo.ItemsImg;
import com.mrdios.foodie.pojo.ItemsParam;
import com.mrdios.foodie.pojo.ItemsSpec;
import com.mrdios.foodie.pojo.vo.ItemCommentCountVo;
import com.mrdios.foodie.pojo.vo.ItemCommentVo;
import com.mrdios.foodie.pojo.vo.ItemInfoVo;
import com.mrdios.foodie.pojo.vo.SearchItemsVo;
import com.mrdios.foodie.service.ItemService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

/**
 * 商品信息接口
 *
 * @author CodePorter
 * @date 2020-06-21
 */
@RestController
@RequestMapping("items")
public class ItemController {
    @Autowired
    private ItemService itemService;

    @GetMapping("/info/{itemId}")
    @ApiOperation(value = "获取商品详情信息", notes = "获取商品详情相关信息")
    public ApiResponse<ItemInfoVo> getItemInfo(@PathVariable String itemId) {
        if (StringUtils.isBlank(itemId)) {
            return ApiResponse.errorMsg("商品id不能为空");
        }
        Items items = itemService.getItemById(itemId);
        List<ItemsImg> itemImgs = itemService.getItemImgs(itemId);
        List<ItemsSpec> itemSpecList = itemService.getItemSpecList(itemId);
        ItemsParam itemParam = itemService.getItemParam(itemId);
        ItemInfoVo itemInfo = new ItemInfoVo();
        itemInfo.setItem(items);
        itemInfo.setItemImgList(itemImgs);
        itemInfo.setItemSpecList(itemSpecList);
        itemInfo.setItemParams(itemParam);
        return ApiResponse.ok(itemInfo);
    }

    @GetMapping("/commentLevel")
    @ApiOperation(value = "获取商品评价数量信息", notes = "获取商品评价数量信息")
    public ApiResponse<ItemCommentCountVo> getItemCommentLevelCount(String itemId) {
        if (StringUtils.isBlank(itemId)) {
            return ApiResponse.errorMsg("商品id不能为空");
        }
        return ApiResponse.ok(itemService.getItemCommentCount(itemId));
    }

    @GetMapping("/comments")
    @ApiOperation(value = "分页查询商品评价", notes = "分页查询商品评价")
    public ApiResponse<PageModel<ItemCommentVo>> getItemCommentLevelCount(String itemId, Integer level, Integer page, Integer pageSize) {
        if (StringUtils.isBlank(itemId)) {
            return ApiResponse.errorMsg("商品id不能为空");
        }
        if (null == page) {
            page = 1;
        }
        if (null == pageSize) {
            pageSize = Constant.PageConstant.DEFAULT_PAGE_SIZE;
        }
        return ApiResponse.ok(itemService.getItemCommentsPage(itemId, level, page, pageSize));
    }

    @GetMapping("/search")
    @ApiOperation(value = "按关键字商品搜索", notes = "按关键字商品搜索")
    public ApiResponse<PageModel<SearchItemsVo>> searchItems(String keywords, String sort, Integer page, Integer pageSize) {
        if (StringUtils.isBlank(keywords)) {
            return ApiResponse.errorMsg("搜索内容不能为空");
        }
        if (null == page) {
            page = 1;
        }
        if (null == pageSize) {
            pageSize = Constant.PageConstant.DEFAULT_SEARCH_PAGE_SIZE;
        }
        return ApiResponse.ok(itemService.searchItems(keywords, sort, page, pageSize));
    }

    @GetMapping("/catItems")
    @ApiOperation(value = "按三级分类商品搜索", notes = "按三级分类商品搜索")
    public ApiResponse<PageModel<SearchItemsVo>> searchItemsByThirdCat(Integer catId, String sort, Integer page, Integer pageSize) {
        if (Objects.isNull(catId)) {
            return ApiResponse.ok(null);
        }
        if (null == page) {
            page = 1;
        }
        if (null == pageSize) {
            pageSize = Constant.PageConstant.DEFAULT_SEARCH_PAGE_SIZE;
        }
        return ApiResponse.ok(itemService.searchItemsByThirdCat(catId, sort, page, pageSize));
    }
}
