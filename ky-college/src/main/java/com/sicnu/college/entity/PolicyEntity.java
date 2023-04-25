package com.sicnu.college.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author ?????
 * @email 2565477149@qq.com
 * @date 2022-09-21 21:55:06
 */
@Data
@TableName("c_policy")
public class PolicyEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 考研政策id
	 */
	@TableId
	private Integer id;
	/**
	 * 政策名字
	 */
	private String name;
	/**
	 * 资源地址
	 */
	private String sourceUrl;
	/**
	 * 学校id
	 */
	private Integer sid;
	/**
	 * 上传时间
	 */
	private Date upDate;

}
