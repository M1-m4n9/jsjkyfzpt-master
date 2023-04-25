package com.sicnu.college.filter;

import com.sicnu.college.securityexception.MyAccessDeniedException;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Iterator;

/**
 * 权限决策判断是否通过权限验证
 *
 * @author 热爱生活の李
 * @version 1.0
 * @since 2022/10/24 12:55
 */
@Configuration
public class CustomAccessDecisionManager implements AccessDecisionManager {




    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<ConfigAttribute> iterator = collection.iterator();
        while (iterator.hasNext()){
            ConfigAttribute configAttribute = iterator.next();
            String needRole = configAttribute.getAttribute();
            for (GrantedAuthority authority : authorities) {
                if (StringUtils.equals(authority.getAuthority(),needRole)){
                    return;
                }
            }
        }
        throw new MyAccessDeniedException("用户没有权限");
//        throw new KyException(CodeEnume.USER_NO_POWER);
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
