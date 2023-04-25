package com.sicnu.gateway.controller;

import com.sicnu.common.exception.CodeEnume;
import com.sicnu.common.exception.KyException;
import com.sicnu.common.utils.R;
import com.sicnu.gateway.service.ValidateCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;

/**
 * TODO
 *
 * @author 热爱生活の李
 * @version 1.0
 * @since 2022/11/11 23:28
 */
@RestController
@RequestMapping("/api/getway")
public class CapchaController {
    @Autowired
    ValidateCodeService validateCodeService;

    @GetMapping("/getCapcha")
    public R getCapcha(){
        R r = null;
        try {
            r =  validateCodeService.createCaptcha();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return r;
    }
    @GetMapping("/checkCapcha")
    public R checkCapcha(String uuid,String code){
        try {
            validateCodeService.checkCaptcha(code,uuid);
        }catch (KyException e){
            return R.error(CodeEnume.VALIDCODE_EROOR_OR_NULL);
        }
        return R.ok();
    }
}
