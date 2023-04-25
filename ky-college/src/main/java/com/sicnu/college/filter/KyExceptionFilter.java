package com.sicnu.college.filter;

import com.sicnu.common.exception.KyException;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

/**
 * TODO
 *
 * @author 热爱生活の李
 * @version 1.0
 * @since 2022/11/1 12:53
 */
@Component
public class KyExceptionFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            filterChain.doFilter(servletRequest,servletResponse);
        }catch (Exception e){
            servletRequest.setAttribute("code",403);
            servletRequest.setAttribute("msg",e.getMessage());
            servletRequest.getRequestDispatcher("/error/kyexception").forward(servletRequest,servletResponse);
        }
    }
}
