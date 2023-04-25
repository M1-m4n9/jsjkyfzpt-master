package com.sicnu.auth.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 
 * 
 * @author ?????
 * @email 2565477149@qq.com
 * @date 2022-09-21 21:36:01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PowerEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private Integer id;
	/**
	 * 
	 */
	private String name;
	/**
	 * 
	 */
	private String url;
	/**
	 *
	 */
	private Integer parentId;

}
