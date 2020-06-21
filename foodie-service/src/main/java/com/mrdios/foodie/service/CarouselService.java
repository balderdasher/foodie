package com.mrdios.foodie.service;

import com.mrdios.foodie.pojo.Carousel;

import java.util.List;

/**
 * 轮播图接口
 *
 * @author CodePorter
 * @date 2020-06-20
 */
public interface CarouselService {

    /**
     * 查询所有轮播图
     *
     * @param isShow 是否展示
     */
    List<Carousel> getAll(Integer isShow);
}
