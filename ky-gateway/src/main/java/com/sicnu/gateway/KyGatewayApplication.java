package com.sicnu.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


// 排除掉数据库配置
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
//注册发现的依赖
@EnableDiscoveryClient
@EnableFeignClients
public class KyGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(KyGatewayApplication.class, args);
    }
}
