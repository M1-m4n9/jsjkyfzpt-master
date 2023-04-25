package com.sicnu.gateway.service.impl;

import com.google.code.kaptcha.Producer;
import com.sicnu.common.exception.CodeEnume;
import com.sicnu.common.exception.KyException;
import com.sicnu.common.utils.Base64;
import com.sicnu.common.utils.Constant;
import com.sicnu.common.utils.R;
import com.sicnu.gateway.config.properties.CaptchaProperties;
import com.sicnu.gateway.service.ValidateCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.UUID;

/**
 * 验证码
 *
 * @author 热爱生活の李
 * @version 1.0
 * @since 2022/11/11 21:07
 */

@Service
public class ValidateCodeServiceImpl implements ValidateCodeService {

    @Resource(name = "captchaProducer")
    private Producer captchaProducer;

    @Resource(name = "captchaProducerMath")
    private Producer captchaProducerMath;


    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private CaptchaProperties captchaProperties;
    @Override
    public R createCaptcha() throws IOException, KyException {
        R r = R.ok();
        Boolean enabled = captchaProperties.getEnabled();
        r.put("captchaEnable",enabled);
        if(!enabled){
            return r;
        }
        String uuid = UUID.randomUUID().toString();
        String verifyKey = Constant.CAPTCHA_CODE_KEY +uuid;

        String capStr = null;
        String code = null;
        BufferedImage image = null;
        String captchaType = captchaProperties.getType();
        if("math".equals(captchaType)){
            String capText = captchaProducerMath.createText();
            capStr = capText.substring(0, capText.lastIndexOf("@"));
            code = capText.substring(capText.lastIndexOf("@")+1);
            image = captchaProducerMath.createImage(capStr);
        }else{
            capStr = code = captchaProducer.createText();
            image = captchaProducer.createImage(capStr);
        }
        redisTemplate.opsForValue().set(verifyKey,code);
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        try {
            ImageIO.write(image,"jpg",os);
        }catch (IOException e){
            return R.error();
        }
        r.put("uuid",uuid);
        r.put("img", Base64.encode(os.toByteArray()));
        return r;
    }

    @Override
    public void checkCaptcha(String code, String uuid) throws KyException {
        if(StringUtils.isEmpty(code)){
            throw new KyException(CodeEnume.VALIDCODE_EROOR_OR_NULL);
        }
        if(StringUtils.isEmpty(uuid)){
            throw new KyException(CodeEnume.VALIDCODE_EROOR_OR_NULL);
        }
        String verifyKey = Constant.CAPTCHA_CODE_KEY+uuid;
        String captcha = redisTemplate.opsForValue().get(verifyKey);
        if(!code.equalsIgnoreCase(captcha)){
            throw new KyException(CodeEnume.VALIDCODE_EROOR_OR_NULL);
        }
    }
}
