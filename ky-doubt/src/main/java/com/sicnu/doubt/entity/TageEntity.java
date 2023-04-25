package com.sicnu.doubt.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sicnu.common.valid.Add;
import com.sicnu.common.valid.Update;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author KingBob
 */
@Data
@TableName("d_tage")
public class TageEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId
    private Integer id;

    @NotEmpty(message = "标签名称未指定",groups = {Add.class, Update.class})
    private String name;

    private String introduction;
}
