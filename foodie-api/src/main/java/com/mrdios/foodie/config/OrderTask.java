package com.mrdios.foodie.config;

import com.mrdios.foodie.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 关闭超时未支付订单定时任务,分布式不采用此种方式
 *
 * @author CodePorter
 * @date 2020-07-05
 */
@Component
@EnableScheduling
public class OrderTask {

    @Autowired
    private OrderService orderService;

    @Scheduled(cron = "0 0 0/1 * * ?")
    public void closeOrder() {
        orderService.closeOrder();
    }
}
