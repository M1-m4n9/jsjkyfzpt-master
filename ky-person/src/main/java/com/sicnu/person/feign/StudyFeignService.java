package com.sicnu.person.feign;


import com.sicnu.common.utils.R;
import com.sicnu.person.feign.entity.CourseCollectionEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author LiuChuang
 * @Date 2022/11/27 16:41
 * @PackageName:com.sicnu.person.feign
 * @ClassName: StudyFeignService
 * @Description: TODO
 * @Version 1.0
 */
@FeignClient("ky-study")
public interface StudyFeignService
{
	
	@GetMapping("study/coursecollection/info")
	R info(@RequestParam Integer id);
	
	@PutMapping("study/coursecollection/update")
	R update(@RequestBody CourseCollectionEntity courseCollection);
}
