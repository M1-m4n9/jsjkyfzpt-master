package com.sicnu.doubt.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.sicnu.common.valid.Add;
import com.sicnu.common.valid.Update;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * (DPostage)表实体类
 *
 * @author makejava
 * @since 2022-12-13 00:18:40
 */
@Data
@TableName("d_postage")
public class PostageEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 帖子标签中间表id
     */
    @TableId
    private Integer id;
    /**
     * 帖子id
     */
    @NotEmpty(message = "帖子id不能为空",groups = {Add.class, Update.class})
    private Integer pid;
    /**
     * 标签id
     */
    @NotEmpty(message = "标签id不能为空",groups = {Add.class, Update.class})
    private Integer taid;

    }

