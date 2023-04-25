package com.sicnu.auth.service;

import com.sicnu.auth.vo.UserLoginVo;
import com.sicnu.common.utils.R;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * TODO
 *
 * @author 热爱生活の李
 * @version 1.0
 * @since 2022/10/23 16:33
 */
public interface UserService extends UserDetailsService {
    R login(UserLoginVo userResponseVo);

    void getMenuAndUrl();
}
