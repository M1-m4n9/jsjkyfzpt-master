package com.sicnu.study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableDiscoveryClient
@EnableScheduling // 开启定时任务
@EnableFeignClients  //开启feign
public class KyStudyApplication {

    public static void main(String[] args) {
        SpringApplication.run(KyStudyApplication.class, args);
    }

}
