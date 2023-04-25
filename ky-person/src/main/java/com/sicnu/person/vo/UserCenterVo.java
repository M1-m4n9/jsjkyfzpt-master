package com.sicnu.person.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author LiuChuang
 * @Date 2022/11/28 16:02
 * @PackageName:com.sicnu.person.vo
 * @ClassName: UserCenterVo
 * @Description: TODO
 * @Version 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCenterVo implements Serializable
{
	private static final long serialVersionUID = 1L;


	/**
	 * 用户名
	 */
	private String username;
	
	/**
	 * 性别(0-女，1-男)
	 */
	private Integer sex;
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
	 * 头像地址
	 */
	private String headUrl;
}
