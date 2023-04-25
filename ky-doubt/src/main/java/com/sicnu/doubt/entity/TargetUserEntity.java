package com.sicnu.doubt.entity;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sicnu.common.valid.Add;
import com.sicnu.common.valid.Update;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;

/**
 * (DTargetUser)表实体类
 *
 * @author makejava
 * @since 2022-12-15 01:12:05
 */
@Data
@TableName("d_target_user")
public class TargetUserEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 主键id
     */
    @TableId
    private Integer id;
    /**
     * 用户id
     */
    @NotEmpty(message = "用户id不能为空",groups = {Add.class, Update.class})
    private Integer uid;
    /**
     * 点赞或收藏的对象id
     */
    @NotEmpty(message = "对象id不能为空",groups = {Add.class, Update.class})
    private Integer entityId;
    /**
     * 点赞或收藏的对象类型
     */
    @NotEmpty(message = "类型不能为空",groups = {Add.class, Update.class})
    private Integer type;
    /**
     * 点赞状态
     */
    private Integer liked;
    /**
     * 收藏状态
     */
    private Integer collected;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

}

