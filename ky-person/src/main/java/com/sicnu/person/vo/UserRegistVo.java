package com.sicnu.person.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 *
 *
 * @author 热爱生活の李
 * @version 1.0
 * @since 2022/10/23 10:46
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistVo implements Serializable
{
    private static final long serialVersionUID = 1L;
    private String username; // 用户名
    private String password1; // 密码
    private String password2; //确认密码
    private String phone; // 电话号码
    private String code; //验证码
}
