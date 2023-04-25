package com.sicnu.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

/**
 * TODO
 *
 * @author 热爱生活の李
 * @version 1.0
 * @since 2022/11/12 10:47
 */
@Configuration
public class CorsConfiguration {
    @Bean
    public CorsWebFilter corsWebFilter(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        org.springframework.web.cors.CorsConfiguration conf = new org.springframework.web.cors.CorsConfiguration();
        // 配置跨域
        // 配置啊那些请求头
        conf.addAllowedHeader("*");
        //配置那些请求方式
        conf.addAllowedMethod("*");
        //配置那些来源的请求
        conf.addAllowedOrigin("*");
        //配置cookie是否可以 true-可以 false-不可以
        conf.setAllowCredentials(true);
        source.registerCorsConfiguration("/**",conf);
        return new CorsWebFilter(source);
    }
}