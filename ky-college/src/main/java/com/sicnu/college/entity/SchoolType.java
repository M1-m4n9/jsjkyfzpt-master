package com.sicnu.college.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TODO
 *
 * @author 热爱生活の李
 * @version 1.0
 * @since 2022/11/9 14:55
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("c_school_type")
public class SchoolType {
    private Integer id;
    private String name;
}
