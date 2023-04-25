package com.sicnu.college.config;

import com.sicnu.college.interceptor.NoLoginedInterceptor;
import com.sicnu.college.interceptor.SavePathInterceptor;
import com.sicnu.college.interceptor.SaveUserInfoInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * TODO
 *
 * @author 热爱生活の李
 * @version 1.0
 * @since 2022/10/24 16:49
 */
@Configuration
public class MyWebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private SavePathInterceptor savePathInterceptor;

    @Autowired
    private NoLoginedInterceptor noLoginedInterceptor;

    @Autowired
    private SaveUserInfoInterceptor saveUserInfoInterceptor;

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOrigins("*")
//                .allowedHeaders("*")
//                .allowedMethods("*")
//                .allowCredentials(true);
//    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry
                .addInterceptor(noLoginedInterceptor);
        registry
                .addInterceptor(savePathInterceptor);
        registry
                .addInterceptor(saveUserInfoInterceptor);
    }
}
