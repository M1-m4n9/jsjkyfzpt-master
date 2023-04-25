package com.sicnu.doubt.vo;

import lombok.Data;

import java.util.List;

/**
 * TODO
 *
 * @author 热爱生活の李
 * @version 1.0
 * @since 2022/12/5 18:45
 */
@Data
public class FollowsVo {
    private long pages;
    private long size;
    private long current;
    private long total;
    List<UserFollowsVo> records;
}
