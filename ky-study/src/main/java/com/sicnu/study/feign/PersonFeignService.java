package com.sicnu.study.feign;


import com.sicnu.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author LiuChuang
 * @Date 2022/12/6 14:10
 * @PackageName:com.sicnu.study.feign
 * @ClassName: PersonFeignService
 * @Description: TODO
 * @Version 1.0
 */
@FeignClient("ky-person")
public interface PersonFeignService
{
	@GetMapping("person/user/info/simple")
	public R simpleInfo(@RequestParam Integer id);
}
