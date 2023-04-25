package com.sicnu.auth.filter;

import com.sicnu.auth.entity.RoleEntity;
import com.sicnu.auth.entity.UserEntity;
import com.sicnu.auth.properties.RsaKeyProperties;
import com.sicnu.common.utils.Constant;
import com.sicnu.common.utils.JwtUtils;
import com.sicnu.common.utils.Payload;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Token校验器
 *
 * @author 热爱生活の李
 * @version 1.0
 * @since 2022/10/23 20:43
 */
public class TokenVerifyFilter extends BasicAuthenticationFilter {
    private RsaKeyProperties prop;

    public TokenVerifyFilter(AuthenticationManager authenticationManager, RsaKeyProperties prop) {
        super(authenticationManager);
        this.prop = prop;
    }

    /**
     * 过滤请求
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        /**
         *  保证了有些时候没有token但是session里面有
         */
        HttpSession session = request.getSession();
        UserEntity u = (UserEntity)session.getAttribute(Constant.AUTH_SESSION_USER_NAME);
        List<SimpleGrantedAuthority> roleNames = new ArrayList<>();
        UsernamePasswordAuthenticationToken authenticationToken = null;
        if(u != null){
            List<RoleEntity> roles = u.getRoles();
            roles.stream().forEach(role->roleNames.add(new SimpleGrantedAuthority(role.getName())));
            authenticationToken = new UsernamePasswordAuthenticationToken(u,null,roleNames);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            chain.doFilter(request,response);
            return;
        }

        String token = request.getHeader(Constant.AUTH_TOKEN_HEADER_NAME);
        // 没有登录
        if(token == null || !token.startsWith(Constant.AUTH_TOKEN_HEADER_RESOURCE_START)){
            chain.doFilter(request,response);
            return;
        }
        //获取信息
        Payload<UserEntity> payload = JwtUtils.getInfoFromToken(token.replace(Constant.AUTH_TOKEN_HEADER_RESOURCE_START, ""), prop.getPublicKey(), UserEntity.class);
        UserEntity user = payload.getUserInfo();
        System.out.println(user.getRoles());
        user.getRoles().stream().forEach(role->roleNames.add(new SimpleGrantedAuthority(role.getName())));
        if(user != null){
            authenticationToken = new UsernamePasswordAuthenticationToken(user, null, roleNames);
        }
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request,response);
    }

}
