package com.sicnu.auth.interceptor;

import com.sicnu.auth.entity.UserEntity;
import com.sicnu.auth.feign.PersonFeignService;
import com.sicnu.auth.properties.RsaKeyProperties;
import com.sicnu.common.exception.KyException;
import com.sicnu.common.utils.Constant;
import com.sicnu.common.utils.JwtUtils;
import com.sicnu.common.utils.Payload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * TODO
 *
 * @author 热爱生活の李
 * @version 1.0
 * @since 2022/10/25 15:32
 */
@Configuration
public class SaveUserInfoInterceptor implements HandlerInterceptor {
    @Autowired
    private PersonFeignService personFeignService;
    @Autowired
    private RsaKeyProperties prop;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader(Constant.AUTH_TOKEN_HEADER_NAME);
        // 说明没有认证
        if(token == null || !token.startsWith(Constant.AUTH_TOKEN_HEADER_RESOURCE_START)){
            return true;
        }
        // 获取session
        HttpSession session = request.getSession();
        // 获取userinfo
        UserEntity userSession = (UserEntity)session.getAttribute(Constant.AUTH_SESSION_USER_NAME);
        //说明是第一次
        if(userSession == null){
            Payload<UserEntity> payload = JwtUtils.getInfoFromToken(token.replace(Constant.AUTH_TOKEN_HEADER_RESOURCE_START, ""), prop.getPublicKey(), UserEntity.class);
            UserEntity user = payload.getUserInfo();
            UserEntity u2 = personFeignService.getUserByName(user.getUsername());
            System.out.println(u2);
            session.setAttribute(Constant.AUTH_SESSION_USER_NAME,u2);
        }
        return true;
    }
}
