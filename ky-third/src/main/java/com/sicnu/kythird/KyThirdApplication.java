package com.sicnu.kythird;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = {"com.sicnu"})
@EnableDiscoveryClient
@ComponentScan({"com.sicnu"})
public class KyThirdApplication {

    public static void main(String[] args) {
        SpringApplication.run(KyThirdApplication.class, args);
    }

}
