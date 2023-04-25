package com.sicnu.college.interceptor;

import com.sicnu.college.entity.PowerEntity;
import com.sicnu.college.feign.PersonFeignService;
import com.sicnu.common.annotation.SaveAuthPath;
import com.sicnu.common.exception.CodeEnume;
import com.sicnu.common.exception.KyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用来保存还没有加入数据库的认证消息
 *
 * @author 热爱生活の李
 * @version 1.0
 * @since 2022/10/24 16:00
 */
@Component
public class SavePathInterceptor implements HandlerInterceptor {
    @Autowired
    private PersonFeignService personFeignService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(handler instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            SaveAuthPath annotation = handlerMethod.getMethodAnnotation(SaveAuthPath.class);
            // 没有注解直接放行
            if(annotation == null)return true;
            //获取注解的值
            String value = annotation.value();
            if(value == null || "".equals(value))throw new KyException(CodeEnume.ANNOTATION_SAVEPATH_ERROR);
            String url = annotation.url();

            // 说明没有自己配置，那么需要自己从request中获取
            if(url == null || "".equals(url) || url.isEmpty()){
                url = request.getRequestURI();
            }
            int parentId = annotation.parentId();
            PowerEntity power = new PowerEntity();
            power.setName(value);
            power.setUrl(url);
            power.setParentId(parentId);
            try {
                personFeignService.saveIfNo(power);
            }catch (Exception e){
                throw new KyException(CodeEnume.FERIGN_PERSON_SERVICE_EXCEPTION);
            }
            return true;
        }
        return true;
    }
}
