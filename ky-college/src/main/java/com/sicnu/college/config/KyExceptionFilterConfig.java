package com.sicnu.college.config;

import com.sicnu.college.filter.KyExceptionFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * TODO
 *
 * @author 热爱生活の李
 * @version 1.0
 * @since 2022/11/1 12:58
 */
@Configuration
public class KyExceptionFilterConfig {
    @Autowired
    private KyExceptionFilter kyExceptionFilter;

    @Bean
    public FilterRegistrationBean registerBusExceptionFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(kyExceptionFilter);
        registration.addUrlPatterns("/*");
        registration.setName("kyExceptionFilter");
        //设置Filter优先级
        registration.setOrder(-1);
        return registration;
    }

}
