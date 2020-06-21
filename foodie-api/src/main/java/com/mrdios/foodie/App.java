package com.mrdios.foodie;

import com.mrdios.foodie.mapper.MyMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * spring boot application runner.
 *
 * @author huxiong
 * @date 2020-06-11
 */
@SpringBootApplication
@MapperScan(basePackages = "com.mrdios.foodie.mapper", markerInterface = MyMapper.class)
@ComponentScan(basePackages = {"com.mrdios", "org.n3r.idworker"})
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
