package com.sicnu.person.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.support.geo.LineString;
import com.sicnu.common.exception.CodeEnume;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.sicnu.person.entity.RoleEntity;
import com.sicnu.person.service.RoleService;
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
@RequestMapping("person/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = roleService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info")
    public R info(@RequestParam Integer id){
		RoleEntity role = roleService.getRoleAndPowerById(id);
        return R.ok().put("role", role);
    }
    /**
     * 查询出这个角色的权限
     */
    @GetMapping("/rolePowerArray")
    public R rolePowerArray(@RequestParam Integer id){
        List<List<Integer>> list = roleService.getRolePowerArray(id);
        return R.ok().put("data",list);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody RoleEntity role){
        System.out.println(role);
		roleService.saveRoleAndPower(role);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody RoleEntity role){
		roleService.updateRoleAndPowerById(role);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids){
        boolean flag = roleService.removeRoleByIds(Arrays.asList(ids));
        if(flag){
            return R.ok();
        }
        return R.error(CodeEnume.DELETE_ROLE_FAIL);
    }

}
