package com.sicnu.auth.interceptor;

import com.sicnu.common.annotation.NoLogined;
import com.sicnu.common.exception.CodeEnume;
import com.sicnu.common.exception.KyException;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * TODO
 *
 * @author 热爱生活の李
 * @version 1.0
 * @since 2022/10/24 21:02
 */
@Configuration
public class NoLoginedInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        if(requestURI.equals("/login")) return true;
        if (handler instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            NoLogined annotation = handlerMethod.getMethodAnnotation(NoLogined.class);
            // 说明没有这个注解那就是需要登录
            if(annotation == null){
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                if(authentication == null)throw new KyException(CodeEnume.USER_NO_LOGINED);
                if(!authentication.isAuthenticated())throw new KyException(CodeEnume.USER_NO_AUTHENTICATION);
                return true;
            }
            return true;
        }
        return true;
    }
}
