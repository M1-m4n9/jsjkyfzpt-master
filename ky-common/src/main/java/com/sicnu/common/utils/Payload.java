package com.sicnu.common.utils;

import lombok.Data;

import java.util.Date;

/**
 * TODO
 *
 * @author 热爱生活の李
 * @version 1.0
 * @since 2022/10/23 17:58
 */
@Data
public class Payload <T>{
    private String id;
    private T userInfo;
    private Date expiration;
}
