package com.mrdios.foodie.api.controller;

import com.mrdios.foodie.common.bean.ApiResponse;
import com.mrdios.foodie.common.enums.YesOrNoEnum;
import com.mrdios.foodie.pojo.Carousel;
import com.mrdios.foodie.pojo.Category;
import com.mrdios.foodie.pojo.vo.CategoryVo;
import com.mrdios.foodie.pojo.vo.NewItemVo;
import com.mrdios.foodie.service.CarouselService;
import com.mrdios.foodie.service.CategoryService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 首页相关接口
 *
 * @author CodePorter
 * @date 2020-06-20
 */
@Slf4j
@RestController
@RequestMapping("index")
public class IndexController {
    @Autowired
    private CarouselService carouselService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/carousel")
    public ApiResponse<List<Carousel>> queryAllBanner() {
        return ApiResponse.ok(carouselService.getAll(YesOrNoEnum.YES.getCode()));
    }

    @GetMapping("/cats")
    public ApiResponse<List<Category>> queryLevelOneCats() {
        return ApiResponse.ok(categoryService.getLevelOneCategories());
    }

    @GetMapping("/subCat/{rootCatId}")
    public ApiResponse<List<CategoryVo>> getSubCats(@PathVariable Integer rootCatId) {
        if (null == rootCatId) {
            return ApiResponse.errorMsg("分类不存在");
        }
        return ApiResponse.ok(categoryService.getSubCatList(rootCatId));
    }

    @ApiOperation(value = "查询一级分类下的6个最新商品", notes = "查询一级分类下的6个最新商品")
    @GetMapping("/sixNewItems/{rootCatId}")
    public ApiResponse<List<NewItemVo>> getNewItemList(@PathVariable Integer rootCatId) {
        if (null == rootCatId) {
            return ApiResponse.errorMsg("分类不存在");
        }
        return ApiResponse.ok(categoryService.get6NewItemList(rootCatId));
    }


}
