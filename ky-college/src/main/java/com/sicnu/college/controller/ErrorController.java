package com.sicnu.college.controller;

import com.sicnu.common.utils.R;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * TODO
 *
 * @author 热爱生活の李
 * @version 1.0
 * @since 2022/11/1 12:55
 */
@RestController
@RequestMapping("/error")
public class ErrorController {

    @RequestMapping("/kyexception")
    public R kyException(HttpServletRequest request){
        return R.error(Integer.valueOf(request.getAttribute("code").toString()),request.getAttribute("msg").toString());
    }
}
