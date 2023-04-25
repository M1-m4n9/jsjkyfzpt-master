package com.sicnu.common.utils;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;

/**
 * 短信发送工具类
 */
//登录
//tccli sms SendSms --cli-unfold-argument --region ap-nanjing
//		--PhoneNumberSet 15579857735 --SmsSdkAppId 1400718699
//		--SignName 怪人友OL公众号 --TemplateId 1500982
//		--TemplateParamSet 123456 5
//注册 --TemplateId 1500986
public class SMSUtils
{
	/**
	 * 发送短信
	 * @param PhoneNumberSet 电话号码
	 * @param SmsSdkAppId smskey
	 * @param SignName 签名
	 * @param TemplateId 模板
	 * @param TemplateParamSet 模板参数
	 */
	public static void sendMessage(String PhoneNumberSet, String SmsSdkAppId,
								   String SignName, String TemplateId, String... TemplateParamSet)
	{
		try
		{
			// 实例化一个认证对象，入参需要传入腾讯云账户secretId，secretKey,此处还需注意密钥对的保密
			// 密钥可前往https://console.cloud.tencent.com/cam/capi网站进行获取
			Credential cred = new Credential("AKIDrXx6yZsuf9MIxLSJtDH4apoWx71e05tR", "IJ4YlhMkrhFWWFhsmPlRr0sTnqc7LIyN");
			// 实例化一个http选项，可选的，没有特殊需求可以跳过
			HttpProfile httpProfile = new HttpProfile();
			httpProfile.setEndpoint("sms.tencentcloudapi.com");
			// 实例化一个client选项，可选的，没有特殊需求可以跳过
			ClientProfile clientProfile = new ClientProfile();
			clientProfile.setHttpProfile(httpProfile);
			// 实例化要请求产品的client对象,clientProfile是可选的
			SmsClient client = new SmsClient(cred, "ap-nanjing", clientProfile);
			// 实例化一个请求对象,每个接口都会对应一个request对象
			SendSmsRequest req = new SendSmsRequest();
			//			String[] phoneNumberSet1 = {"15579857735"};
			String[] phoneNumberSet1 = {PhoneNumberSet};
			req.setPhoneNumberSet(phoneNumberSet1);
			
			//			req.setSmsSdkAppId("1400718699");
			req.setSmsSdkAppId(SmsSdkAppId);
			//			req.setSignName("怪人友OL公众号");
			req.setSignName(SignName);
			//			req.setTemplateId("1500982");
			req.setTemplateId(TemplateId);
			
			//			String[] templateParamSet1 = {"123456", "5"};
			//			String[] templateParamSet1 = {TemplateParamSet[0].toString(), TemplateParamSet[1]};
			String[] templateParamSet1 = TemplateParamSet;
			req.setTemplateParamSet(templateParamSet1);
			
			// 返回的resp是一个SendSmsResponse的实例，与请求对象对应
			SendSmsResponse resp = client.SendSms(req);
			// 输出json格式的字符串回包
			System.out.println(SendSmsResponse.toJsonString(resp));
		}
		catch (TencentCloudSDKException e)
		{
			System.out.println(e.toString());
		}
	}
}

