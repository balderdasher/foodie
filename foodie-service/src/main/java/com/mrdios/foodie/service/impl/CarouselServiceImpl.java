package com.mrdios.foodie.service.impl;

import com.mrdios.foodie.mapper.CarouselMapper;
import com.mrdios.foodie.pojo.Carousel;
import com.mrdios.foodie.service.CarouselService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author CodePorter
 * @date 2020-06-20
 */
@Service
public class CarouselServiceImpl implements CarouselService {
    @Autowired
    private CarouselMapper carouselMapper;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Carousel> getAll(Integer isShow) {
        Example example = new Example(Carousel.class);
        example.orderBy("sort").desc();
        example.createCriteria().andEqualTo("isShow", isShow);
        return carouselMapper.selectByExample(example);
    }
}
