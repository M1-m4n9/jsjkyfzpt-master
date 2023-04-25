package com.sicnu.person.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.sicnu.common.exception.CodeEnume;
import com.sicnu.person.entity.RoleEntity;
import com.sicnu.person.vo.MenuResponseVo;
import com.sicnu.person.vo.VueValueAndLabel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.sicnu.person.entity.PowerEntity;
import com.sicnu.person.service.PowerService;
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
@RequestMapping("person/power")
public class PowerController {
    @Autowired
    private PowerService powerService;


    /**
     * 获取边框的菜单
     */
    @GetMapping("/getMenuAndUrl")
    public R getMenuAndUrl(){
        List<MenuResponseVo> menus = powerService.getMenuAndUrl();
        return R.ok().put("data",menus);
    }
    /**
     * 获取所有的权限
     */
    @GetMapping("/getAllPower")
    public R getAllPower(){
        List<MenuResponseVo> powers = powerService.getAllPower();
        return R.ok().put("data",powers);
    }
    /**
     * 获取权限
     */
    @GetMapping("/getVueValueAndLabel")
    public R getVueValueAndLabel(){
        List<VueValueAndLabel> vueValueAndLabel = powerService.getVueValueAndLabel();
        return R.ok().put("data",vueValueAndLabel);
    }

    /**
     * 为远程调用保存url时候的方法
     * @param power
     * @return
     */
    @RequestMapping("/saveIfNo")
    public R saveIfNo(@RequestBody PowerEntity power){
        powerService.saveIfNo(power);
        return R.ok();
    }

    @RequestMapping("/getRolesByUrl")
    public List<RoleEntity> getRolesByUrl(@RequestBody String url){
        List<RoleEntity> roles= powerService.getRolesByUrl(url);
        return roles;
    }


    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = powerService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info")
    public R info(@RequestParam Integer id){
		PowerEntity power = powerService.getById(id);

        return R.ok().put("power", power);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody PowerEntity power){
		powerService.save(power);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody PowerEntity power){
		powerService.updateById(power);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids){
        boolean flag = powerService.removePowerByIds(Arrays.asList(ids));
        if(flag){
            return R.ok();
        }
        return R.error(CodeEnume.DELETE_POWER_FAIL);
    }

}
