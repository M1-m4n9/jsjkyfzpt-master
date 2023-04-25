package com.sicnu.common.utils;

import org.jasypt.util.text.BasicTextEncryptor;

/**
 * @Author LiuChuang
 * @Date 2023/1/6 9:00
 * @PackageName:com.sicnu.common.utils
 * @ClassName: JasyptUtil
 * @Description: TODO
 * @Version 1.0
 */
public class JasyptUtil
{
	public static void encrypt(String account, String password, String secret)
	{
		BasicTextEncryptor encryptor = new BasicTextEncryptor();
		System.out.println("账号为：" + account);
		System.out.println("密码为：" + password);
		System.out.println("密钥为：" + secret);
		
		//指定密钥
		encryptor.setPassword(secret);
		
		//加密后的账号
		String newAccount = encryptor.encrypt(account);
		//加密后的密码
		String newPassword = encryptor.encrypt(password);
		System.out.println("加密后：\n" + newAccount + "\n" + newPassword + "\n========================================");
		
		String acc=encryptor.decrypt(newAccount);
		String pass=encryptor.decrypt(newPassword);
		System.out.println("解密后：\n" + acc + "\n" + pass + "\n========================================");
	}
}
