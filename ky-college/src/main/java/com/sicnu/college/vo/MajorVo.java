package com.sicnu.college.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TODO
 *
 * @author 热爱生活の李
 * @version 1.0
 * @since 2022/10/9 16:54
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MajorVo {
    /**
     * id
     */
    private Integer id;
    /**
     * 专业名称
     */
    private String name;
    /**
     * 专业代码
     */
    private String majorCode;
    /**
     * 专业简介
     */
    private String introduction;
    /**
     * 那个学校
     */
    private String schoolName;
    /**
     * 专业类型
     */
    private String majorType;
    /**
     * 那个院
     */
    private String department;
}
