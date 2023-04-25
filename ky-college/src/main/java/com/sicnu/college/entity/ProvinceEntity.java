package com.sicnu.college.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * TODO
 *
 * @author 热爱生活の李
 * @version 1.0
 * @since 2022/10/16 10:06
 */
@Data
@TableName("c_province")
public class ProvinceEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId
    private Integer id;
    private String province;
}
