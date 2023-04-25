package com.sicnu.study.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sicnu.common.valid.Add;
import com.sicnu.common.valid.Update;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @Author LiuChuang
 * @Date 2022/11/21 14:15
 * @PackageName:com.sicnu.study.entity
 * @ClassName: CollectionClassificationEntity
 * @Description: TODO
 * @Version 1.0
 */
@Data
@TableName("s_collection_classification")
public class CollectionClassificationEntity implements Serializable
{
    private static final long serialVersionUID = 1L;

    /**
     * 合集分类id
     */
    @TableId
    private Integer id;

    /**
     * 类型名称
     */
    @NotEmpty(message = "类型名称eg:计算机网络", groups = {Add.class, Update.class})
    private String name;
}
