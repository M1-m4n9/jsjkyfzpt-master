package com.sicnu.person.vo;

import lombok.Data;

/**
 * TODO
 *
 * @author 热爱生活の李
 * @version 1.0
 * @since 2022/12/5 18:28
 */
@Data
public class UserFollowsVo {
    //用户id
    private Integer uid;
    // 头像
    private String headUrl;

    //个人介绍
    private String introduce;

    //名称
    private String username;

    //status 0 关注  1 互相关注
    private Integer status;
}
