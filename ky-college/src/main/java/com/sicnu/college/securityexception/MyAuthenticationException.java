package com.sicnu.college.securityexception;

import org.springframework.security.core.AuthenticationException;

/**
 * 自定义异常(认证)
 *
 * @author 热爱生活の李
 * @version 1.0
 * @since 2022/11/1 13:18
 */
public class MyAuthenticationException extends AuthenticationException {
    public MyAuthenticationException(String msg, Throwable t) {
        super(msg, t);
    }
}
