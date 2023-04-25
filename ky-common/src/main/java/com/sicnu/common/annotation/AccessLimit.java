package com.sicnu.common.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 接口防刷的注解
 *
 * @author 热爱生活の李
 * @version 1.0
 * @since 2022/10/24 21:02
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AccessLimit {

    // 时间
    int second() default 60;
    // 最大次数
    int maxCount() default 5;
}
