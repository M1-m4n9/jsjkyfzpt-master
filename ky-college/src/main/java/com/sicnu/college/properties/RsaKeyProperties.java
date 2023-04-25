package com.sicnu.college.properties;

import com.sicnu.common.utils.RsaUtils;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * TODO
 *
 * @author 热爱生活の李
 * @version 1.0
 * @since 2022/10/23 18:31
 */
@Data
@Configuration
public class RsaKeyProperties {

    @Value("${ky.key.pubKeyPath}")
    private String pubKeyPath;
    @Value("${ky.key.priKeyPath}")
    private String priKeyPath;
    private PublicKey publicKey;
    private PrivateKey privateKey;
    @PostConstruct
    public void loadKey() throws Exception {
        publicKey = RsaUtils.getPublicKey(pubKeyPath);
        privateKey = RsaUtils.getPrivateKey(priKeyPath);
    }
}
