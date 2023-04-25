package com.sicnu.college.securityexception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 *
 * @author 热爱生活の李
 * @version 1.0
 * @since 2022/11/1 13:23
 */
@Component
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        PrintWriter out = response.getWriter();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", HttpServletResponse.SC_FORBIDDEN);
        map.put("message", authException.getMessage());
        out.write(new ObjectMapper().writeValueAsString(map));
        out.flush();
        out.close();
    }
}
