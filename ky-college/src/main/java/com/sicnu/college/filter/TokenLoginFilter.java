package com.sicnu.college.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sicnu.college.entity.RoleEntity;
import com.sicnu.college.entity.UserEntity;
import com.sicnu.college.properties.RsaKeyProperties;
import com.sicnu.common.exception.CodeEnume;
import com.sicnu.common.exception.KyException;
import com.sicnu.common.utils.Constant;
import com.sicnu.common.utils.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 认证过滤器
 *
 * @author 热爱生活の李
 * @version 1.0
 * @since 2022/10/23 20:06
 */
public class TokenLoginFilter extends UsernamePasswordAuthenticationFilter {


    private AuthenticationManager authenticationManager;
    private RsaKeyProperties prop;

    public TokenLoginFilter(AuthenticationManager authenticationManager, RsaKeyProperties prop)
    {
        this.authenticationManager = authenticationManager;
        this.prop = prop;
    }

    /**
     * 接收到并解析前端数据
     * @param request
     * @param res
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse res) throws AuthenticationException {
        try {
            UserEntity user = new ObjectMapper().readValue(request.getInputStream(), UserEntity.class);
            String username = user.getUsername();
            String password = user.getPassword();

            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
            return authenticate;
        }catch (Exception e){
            res.setContentType("application/json;charset=utf-8");
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            PrintWriter out = null;
            try {
                out = res.getWriter();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("code", HttpServletResponse.SC_UNAUTHORIZED);
            map.put("message", "账号或密码错误！");
            try {
                out.write(new ObjectMapper().writeValueAsString(map));
            } catch (JsonProcessingException ex) {
                ex.printStackTrace();
            }
            out.flush();
            out.close();
            throw new KyException(CodeEnume.USER_AUTH_TOKEN_FILTER_ERROR);
        }


    }

    /**
     * 认证成功
     * @param request
     * @param res
     * @param chain
     * @param authResult
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse res, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        /**
         * 得到当前认证的用户对象
         */

        UserEntity user = new UserEntity();
        user.setUsername(authResult.getName());
        user.setRoles((List<RoleEntity>) authResult.getAuthorities());
        System.out.println("successfulAuthentication==>"+user);
        //构建Token
        String token = JwtUtils.generateTokenExpireInMinutes(user, prop.getPrivateKey(), 24 * 60);
        System.out.println("token===>"+token);
        res.addHeader(Constant.AUTH_TOKEN_HEADER_NAME,Constant.AUTH_TOKEN_HEADER_RESOURCE_START+token);
        try {
//登录成功時，返回json格式进行提示
            res.setContentType("application/json;charset=utf-8");
            res.setStatus(HttpServletResponse.SC_OK);
            PrintWriter out = res.getWriter();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("code", HttpServletResponse.SC_OK);
            map.put("message", "登陆成功!");
            out.write(new ObjectMapper().writeValueAsString(map));
            out.flush();
            out.close();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}
