package com.sicnu.auth.feign;

import com.sicnu.auth.entity.PowerEntity;
import com.sicnu.auth.entity.RoleEntity;
import com.sicnu.auth.entity.UserEntity;
import com.sicnu.auth.vo.UserLoginVo;
import com.sicnu.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * TODO
 *
 * @author 热爱生活の李
 * @version 1.0
 * @since 2022/10/23 12:52
 */
@FeignClient("ky-person")
public interface PersonFeignService {
    @PostMapping("/person/user/login")
    R login(@RequestBody UserLoginVo userLoginVo);

    @PostMapping("/person/user/getUserByName")
    UserEntity getUserByName(@RequestBody String s);

    @RequestMapping("/person/power/getRolesByUrl")
    List<RoleEntity> getRolesByUrl(@RequestBody String url);

    @RequestMapping("/person/power/saveIfNo")
    R saveIfNo(@RequestBody PowerEntity power);

    @GetMapping("/person/user/getMenuAndUrl")
    R getMenuAndUrl();

}
