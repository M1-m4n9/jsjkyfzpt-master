package com.sicnu.auth.controller;


import com.sicnu.auth.service.UserService;
import com.sicnu.common.annotation.NoLogined;
import com.sicnu.common.annotation.SaveAuthPath;
import com.sicnu.common.utils.R;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * TODO
 *
 * @author 热爱生活の李
 * @version 1.0
 * @since 2022/10/23 12:42
 */
@RestController
@RequestMapping("/auth/user")
public class UserController {
    @Autowired
    private UserService userService;



    @GetMapping("/getMenuAndUrl")
    public R getMenuAndUrl(){
        userService.getMenuAndUrl();
        return R.ok();
    }

    @PostMapping("/login")
    public void t(){
        System.out.println("的呢管理");
    }

    @GetMapping("/test1")
    public void test1(){
        System.out.println("test1");
    }


    @GetMapping("/test2")
    public void test2(HttpServletRequest request){
        System.out.println("test2");
    }

    @GetMapping("/test3")
    @SaveAuthPath(value = "注册",url = "/a/a",parentId = 20)
    @NoLogined
    public void test3(){
        System.out.println("test3");
    }
}
