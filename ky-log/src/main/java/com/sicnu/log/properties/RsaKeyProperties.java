package com.sicnu.log.properties;

import com.sicnu.common.utils.RsaUtils;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.security.PublicKey;

/**
 * @Author LiuChuang
 * @Date 2022/11/15 10:54
 * @PackageName:com.sicnu.log
 * @ClassName: RsaKeyProperties
 * @Description: 秘钥
 * @Version 1.0
 */
@Data
public class RsaKeyProperties
{
	
	@Value("key/rsa_key.pub")
	private String pubKeyPath;
	private PublicKey publicKey;
	
	@PostConstruct
	public void loadKey() throws Exception
	{
		publicKey = RsaUtils.getPublicKey(pubKeyPath);
	}
}