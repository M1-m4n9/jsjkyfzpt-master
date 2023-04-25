package com.sicnu.kythird.controller;

import com.aliyun.oss.OSS;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import com.sicnu.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * 文件上传
 *
 * @author 热爱生活の李
 * @version 1.0
 * @since 2022/9/24 20:48
 */
@RestController
@RequestMapping("/third/oss")
@Slf4j
@CrossOrigin
public class OssController {

	@Autowired
	private OSS ossClient;
	
	@Value("${spring.cloud.alicloud.oss.endpoint}")
	private String endpoint;
	
	@Value("${spring.cloud.alicloud.oss.bucket}")
	private String bucket;
	@Value("${spring.cloud.alicloud.access-key}")
	private String accessId;


	@GetMapping("/quickUp")
	public R quickUp(@RequestParam String fileName){
		boolean flag = ossClient.doesObjectExist(bucket, fileName);
		return R.ok().put("data",flag);
	}



	/**
	 * 用于web端获取文件上传凭证
	 *
	 * @return
	 */
	@GetMapping("/policy")
	public R policy() {
		String host = "https://" + bucket + "." + endpoint;
		String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		String dir = date + "/";
		try
		{
			long expireTime = 60;
			long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
			Date expiration = new Date(expireEndTime);
			PolicyConditions policyConds = new PolicyConditions();
			policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
			policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);
			String postPolicy = ossClient.generatePostPolicy(expiration, policyConds);
			byte[] binaryData = postPolicy.getBytes("utf-8");
			String encodedPolicy = BinaryUtil.toBase64String(binaryData);
			String postSignature = ossClient.calculatePostSignature(postPolicy);
			Map<String, String> respMap = new LinkedHashMap<String, String>();
			respMap.put("accessId", accessId);
			respMap.put("policy", encodedPolicy);
			respMap.put("signature", postSignature);
			respMap.put("dir", dir);
			respMap.put("host", host);
			respMap.put("expire", String.valueOf(expireEndTime / 1000));
			return R.ok().put("data", respMap);
		} catch (Exception e)
		{
			log.error("web端获取文件上传凭证失败，原因为 : {}", e.getMessage());
			return null;
		}
	}
}