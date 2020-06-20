package com.zeal.zealsay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
*@description zealsay项目启动入口
*@author zeal
*@date 2018-04-25 21:54
*@version 1.0.0
*/
@EnableScheduling //开启定时任务
@EnableAsync    //开启异步线程池
@EnableCaching  //开启缓存
@EnableFeignClients  //开启Feigin
@SpringBootApplication
public class ZealsayApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZealsayApplication.class, args);
    }
}
