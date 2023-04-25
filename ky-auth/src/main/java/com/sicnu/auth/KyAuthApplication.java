package com.sicnu.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;


@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
@EnableGlobalMethodSecurity(securedEnabled = true)
public class KyAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(KyAuthApplication.class, args);
    }

}
