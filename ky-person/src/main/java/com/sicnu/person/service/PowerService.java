package com.sicnu.person.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sicnu.common.utils.PageUtils;
import com.sicnu.person.entity.PowerEntity;
import com.sicnu.person.entity.RoleEntity;
import com.sicnu.person.vo.MenuResponseVo;
import com.sicnu.person.vo.VueValueAndLabel;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author ?????
 * @email 2565477149@qq.com
 * @date 2022-09-21 21:36:01
 */
public interface PowerService extends IService<PowerEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<RoleEntity> getRolesByUrl(String url);

    void saveIfNo(PowerEntity power);

    List<MenuResponseVo> getMenuAndUrl();

    List<MenuResponseVo> getAllPower();

    List<VueValueAndLabel> getVueValueAndLabel();

    boolean removePowerByIds(List<Integer> asList);
}

