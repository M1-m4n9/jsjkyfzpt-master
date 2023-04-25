package com.sicnu.gateway.service;

import com.sicnu.common.exception.KyException;
import com.sicnu.common.utils.R;

import java.io.IOException;

/**
 * TODO
 *
 * @author 热爱生活の李
 * @version 1.0
 * @since 2022/11/11 21:06
 */
public interface ValidateCodeService {
    /**
     * 生成验证码
     */
    public R createCaptcha() throws IOException, KyException;

    /**
     * 校验验证码
     */
    public void checkCaptcha(String key, String value) throws KyException;
}
