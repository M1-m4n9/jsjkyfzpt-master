package com.sicnu.college.vo;

import lombok.Data;

/**
 *
 *
 * @author 热爱生活の李
 * @version 1.0
 * @since 2022/10/23 10:46
 */

@Data
public class UserRegistVo {
    private String username;
    private String password;
    private String phone;
    private String code;
}
