package com.sicnu.auth.config;

import com.sicnu.auth.interceptor.NoLoginedInterceptor;
import com.sicnu.auth.interceptor.SavePathInterceptor;
import com.sicnu.auth.interceptor.SaveUserInfoInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
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
