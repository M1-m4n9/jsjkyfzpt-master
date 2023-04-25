package com.example.springserver1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@SpringBootApplication
@EnableRedisHttpSession
public class SpringServer1Application {
    public static void main(String[] args) {
        SpringApplication.run(SpringServer1Application.class, args);
    }
}
