package com.mrdios.foodie.api.controller;

import com.mrdios.foodie.pojo.Stu;
import com.mrdios.foodie.service.StuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author CodePorter
 * @date 2020-06-13
 */
@RestController
public class StuController {
    @Autowired
    private StuService stuService;

    @GetMapping("/stu/{id}")
    public Stu getStu(@PathVariable Integer id) {
        return stuService.getStu(id);
    }
}
