package com.sicnu.gateway.config.properties;

/**
 * TODO
 *
 * @author 热爱生活の李
 * @version 1.0
 * @since 2022/11/11 21:27
 */

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * 验证码配置
 *
 * @author ruoyi
 */
@Configuration
@RefreshScope
@ConfigurationProperties(prefix = "security.captcha")
public class CaptchaProperties
{
    /**
     * 验证码开关
     */
    private Boolean enabled;

    /**
     * 验证码类型（math 数组计算 char 字符）
     */
    private String type;

    public Boolean getEnabled()
    {
        return enabled;
    }

    public void setEnabled(Boolean enabled)
    {
        this.enabled = enabled;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }
}
