package com.sicnu.college.securityexception;

import org.springframework.security.access.AccessDeniedException;

/**
 * 自定义异常(拒绝策略)
 *
 * @author 热爱生活の李
 * @version 1.0
 * @since 2022/11/1 13:18
 */
public class MyAccessDeniedException extends AccessDeniedException {
    public MyAccessDeniedException(String msg) {
        super(msg);
    }
}
