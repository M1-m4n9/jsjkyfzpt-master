package com.sicnu.search.vo;

import com.sicnu.common.to.CollegEsModel;
import lombok.Data;

import java.util.List;

/**
 * TODO
 *
 * @author 热爱生活の李
 * @version 1.0
 * @since 2022/9/25 16:57
 */
@Data
public class CollegeResponse {
    private List<CollegEsModel> collegs;
    // 当前页
    private Integer pageNum;
    // 总记录树
    private Long total;
    // 总页树
    private Integer totalPage;
}
