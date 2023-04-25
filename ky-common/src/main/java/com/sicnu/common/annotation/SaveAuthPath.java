package com.sicnu.common.annotation;


import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 为了快速保存path到数据库
 *
 * @author 热爱生活の李
 * @version 1.0
 * @since 2022/10/24 21:02
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SaveAuthPath {
    // 权限名称
    String value() default "";
    // 权限url
    String url() default "";
    //权限父亲id
    int parentId() default 0;


    String message() default "{com.sicnu.auth.annotation.SaveAuthPath.message}";
    Class<?> [] groups() default {};
    Class<? extends Payload> [] payload() default  {};
}
