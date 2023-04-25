package com.sicnu.auth.service.Impl;

import com.sicnu.auth.entity.RoleEntity;
import com.sicnu.auth.entity.UserEntity;
import com.sicnu.auth.feign.PersonFeignService;
import com.sicnu.auth.service.UserService;
import com.sicnu.auth.vo.UserLoginVo;
import com.sicnu.common.exception.CodeEnume;
import com.sicnu.common.exception.KyException;
import com.sicnu.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * TODO
 *
 * @author 热爱生活の李
 * @version 1.0
 * @since 2022/10/23 16:34
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private PersonFeignService personFeignService;

    @Override
    public R login(UserLoginVo userResponseVo) {
        R login = null;
        try {
            login = personFeignService.login(userResponseVo);
        }catch (Exception e){
            throw new KyException(CodeEnume.FERIGN_PERSON_SERVICE_EXCEPTION);
        }
        return login;
    }

    @Override
    public void getMenuAndUrl() {

    }


    // SpringSecurity中的查询方法
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 如果为空直接返回null
        if(username== null ||username.isEmpty()) {return null;}
        UserEntity user = null;
        try {
            user = personFeignService.getUserByName(username);
        }catch (Exception e){
            // 远程调用出错
            e.printStackTrace();
            throw new KyException(CodeEnume.FERIGN_PERSON_SERVICE_EXCEPTION);
        }
        if(user == null) {return null;}
        // 获取角色
//        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        // 获取角色
        List<RoleEntity> roles = user.getRoles();
//        roles.stream().forEach(r->authorities.add(new SimpleGrantedAuthority(r.getName())));
        // noop 代表密码没有加密
//        return new User(user.getUsername(),"{noop}"+user.getPassword(),roles);
        return new User(username,"{noop}"+user.getPassword(),roles);
    }
}
