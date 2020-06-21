package com.mrdios.foodie.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author CodePorter
 * @date 2020-06-13
 */
@RestController
public class TestAction {

    @GetMapping("/echo")
    public String wrap() {
        return "Hello World.";
    }
}
