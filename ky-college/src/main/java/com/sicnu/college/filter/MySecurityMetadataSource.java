package com.sicnu.college.filter;

import com.sicnu.college.entity.RoleEntity;
import com.sicnu.college.feign.PersonFeignService;
import com.sicnu.common.exception.CodeEnume;
import com.sicnu.common.exception.KyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 获取权限资源的接口
 * 访问一个 URL时候返回这个接口所需要的权限
 * @author 热爱生活の李
 * @version 1.0
 * @since 2022/10/24 12:57
 */
@Component
public class MySecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    @Autowired
    private PersonFeignService personFeignService;

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        FilterInvocation fi = (FilterInvocation) o;
        // 获取Request
        HttpServletRequest request = ((FilterInvocation) o).getRequest();

        //获取url
        String requestURI = request.getRequestURI();
        List<RoleEntity> roles = null;
        try {
            roles = personFeignService.getRolesByUrl(requestURI);
        }catch (Exception e){
            throw new KyException(CodeEnume.FERIGN_PERSON_SERVICE_EXCEPTION);
        }
        if(roles == null) {return null;}
        Set<ConfigAttribute> set = new HashSet<>();
        roles.stream().forEach(role->set.add(new SecurityConfig(role.getName())));
        return set;
    }

    /**
     * 返回所有定义的权限资源
     * @return
     */
    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    /**
     * 返回类对象是否支持校验
     * @param aClass
     * @return
     */
    @Override
    public boolean supports(Class<?> aClass) {
        return FilterInvocation.class.isAssignableFrom(aClass);
    }
}
