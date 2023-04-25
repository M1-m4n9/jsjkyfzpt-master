package com.sicnu.college.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * TODO
 *
 * @author 热爱生活の李
 * @version 1.0
 * @since 2022/10/9 19:25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentCatalogVo {
    /**
     * 院的id
     */
    private Integer departmentId;
    /**
     * 院的名字
     */
    private String departmentName;
    /**
     * 院下的专业集合
     */
    private List<MajorVo> majorVos;
}
