package com.sicnu.person.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sicnu.person.entity.RolePowerEntity;
import com.sicnu.person.service.RolePowerService;
import com.sicnu.common.utils.PageUtils;
import com.sicnu.common.utils.R;



/**
 * 
 *
 * @author ?????
 * @email 2565477149@qq.com
 * @date 2022-09-21 21:36:01
 */
@RestController
@RequestMapping("person/rolepower")
public class RolePowerController {
    @Autowired
    private RolePowerService rolePowerService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = rolePowerService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id){
		RolePowerEntity rolePower = rolePowerService.getById(id);

        return R.ok().put("rolePower", rolePower);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody RolePowerEntity rolePower){
		rolePowerService.save(rolePower);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody RolePowerEntity rolePower){

		rolePowerService.updateById(rolePower);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids){
		rolePowerService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
