//package com.sicnu.person.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.RedisSerializer;
//import org.springframework.session.web.http.CookieSerializer;
//import org.springframework.session.web.http.DefaultCookieSerializer;
//
///**
// * TODO
// *
// * @author 热爱生活の李
// * @version 1.0
// * @since 2022/10/23 14:57
// */
//@Configuration
//public class SpringSessionConfig {
//
//
//    // 设置cookie
//    @Bean
//    public CookieSerializer cookieSerializer() {
//        DefaultCookieSerializer serializer = new DefaultCookieSerializer();
//        serializer.setCookieName("KYSESSION");
//        serializer.setCookiePath("/");
//        serializer.setDomainNamePattern("^.+?\\.(\\w+\\.[a-z]+)$");
//        return serializer;
//    }
//    // 设置序列化方式
//    @Bean
//    public RedisSerializer<Object> springSessionDefaultRedisSerializer(){
//        return new GenericJackson2JsonRedisSerializer();
//    }
//}
