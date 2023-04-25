package com.sicnu.college.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * TODO
 *
 * @author 热爱生活の李
 * @version 1.0
 * @since 2022/10/9 16:51
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MajorCatalogVo {
    /**
     * 专业类型 id
     *
     */
    private Integer majorTypeId;
    /**
     * 专业类型
     */
    private String majorType;
    /**
     * 专业类型下的各个专业
     */
    private List<MajorVo> majorVos;
}
