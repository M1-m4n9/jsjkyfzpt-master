package com.sicnu.doubt.feign;


import com.sicnu.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient("ky-person")
public interface PersonClients {
    /**
     * 调取person服务获取信息
     * @param id
     * @return
     */
    @GetMapping("person/user/info/simple")
    R info(@RequestParam Integer id);

    @GetMapping("person/userfollows/list/follow")
    R follow(@RequestParam Map<String, Object> params);
}
