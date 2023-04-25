package com.sicnu.common.utils;

import org.springframework.util.DigestUtils;

/**
 * TODO
 *
 * @author 热爱生活の李
 * @version 1.0
 * @since 2022/11/1 9:14
 */
public class MD5Utils {
    public static String MD5(String msg){
        if(msg == null){return null;}
        return DigestUtils.md5DigestAsHex(msg.getBytes());
    }

    public static void main(String[] args) {
        System.out.println(MD5("123456789"));
    }
}
