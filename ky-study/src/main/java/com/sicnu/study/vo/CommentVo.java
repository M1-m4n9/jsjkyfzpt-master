package com.sicnu.study.vo;

import com.sicnu.study.entity.CommentEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author LiuChuang
 * @Date 2022/12/6 13:59
 * @PackageName:com.sicnu.study.vo
 * @ClassName: CommentVo
 * @Description: TODO
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentVo extends CommentEntity
{
	private Integer id;
	private String username;
	private String headUrl;
	private String entityUserName;
	private String entityUserHeadUrl;
}
