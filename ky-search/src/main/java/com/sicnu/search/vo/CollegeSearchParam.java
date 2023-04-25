package com.sicnu.search.vo;

import lombok.Data;
import org.springframework.stereotype.Service;

/**
 * TODO
 *
 * @author 热爱生活の李
 * @version 1.0
 * @since 2022/9/25 16:27
 */
@Data
public class CollegeSearchParam {
    //页面传递过来的全文匹配关键字
    private String keyword;
    //
    private String province;
    //
    private String collegeType;
    //
    private String collegeAttribute;
    //
    private Integer pageNum;
    //
    private Integer pageSize;

}
