package com.sicnu.auth.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TODO
 *
 * @author 热爱生活の李
 * @version 1.0
 * @since 2022/10/23 12:41
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginVo {
    private String username;
    private String password;
}
