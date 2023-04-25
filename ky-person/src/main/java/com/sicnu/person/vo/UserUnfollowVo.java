package com.sicnu.person.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author LiuChuang
 * @Date 2022/11/27 15:31
 * @PackageName:com.sicnu.person.vo
 * @ClassName: UserUnfollowVo
 * @Description: TODO
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUnfollowVo implements Serializable
{
	private static final long serialVersionUID = 1L;
	private Integer uid;
	private Integer[] ids;
}
