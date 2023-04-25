package com.sicnu.auth.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 返回给前端的User
 *
 * @author 热爱生活の李
 * @version 1.0
 * @since 2022/10/23 13:12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseVo implements Serializable {
    /**
     * 用户名
     */
    private String username;
    /**
     * 性别(0-女，1-男)
     */
    private Integer sex;
    /**
     * 出生日期
     */
    private Date birthday;
    /**
     * 地区
     */
    private String region;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 个人简介
     */
    private String introduction;
    /**
     * 粉丝数量
     */
    private Integer fansCount;
    /**
     * 关注数量
     */
    private Integer followCount;
    /**
     * 访问量
     */
    private Integer visitCount;
    /**
     * 专业名称
     */
    private String majorName;
    /**
     * 学校名字
     */
    private String schoolName;
    /**
     * 0-学生,1-老师，-1管理员
     */
    private Integer type;
    /**
     * 是否认证(0-没有 1-有)
     */
    private Integer isAuth;

    /**
     * 头像地址
     */
    private String headUrl;
    /**
     * 创建时间
     */
    private Date createTime;
}
