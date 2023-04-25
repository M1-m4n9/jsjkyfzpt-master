package com.sicnu.auth.config;

import com.sicnu.auth.filter.CustomAccessDecisionManager;
import com.sicnu.auth.filter.MySecurityMetadataSource;
import com.sicnu.auth.filter.TokenLoginFilter;
import com.sicnu.auth.filter.TokenVerifyFilter;
import com.sicnu.auth.properties.RsaKeyProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

/**
 * TODO
 *
 * @author 热爱生活の李
 * @version 1.0
 * @since 2022/10/23 18:40
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private RsaKeyProperties prop;
    @Autowired
    private CustomAccessDecisionManager customAccessDecisionManager;
    @Autowired
    private MySecurityMetadataSource mySecurityMetadataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/auth/user/test2").permitAll()
                .anyRequest().authenticated()
                .withObjectPostProcessor(
                        new ObjectPostProcessor<FilterSecurityInterceptor>() {
                            @Override
                            public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                                o.setSecurityMetadataSource(mySecurityMetadataSource);
                                o.setAccessDecisionManager(customAccessDecisionManager);
                                return o;
                            }
                        }
                );

        http
                .formLogin()
                .loginProcessingUrl("/auth/user/login")
                .permitAll()
                .and()
                .addFilter(new TokenLoginFilter(authenticationManager(),prop))
                .addFilter(new TokenVerifyFilter(authenticationManager(),prop))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }


}
