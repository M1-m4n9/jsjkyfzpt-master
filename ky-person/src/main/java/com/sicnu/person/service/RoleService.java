package com.sicnu.person.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sicnu.common.utils.PageUtils;
import com.sicnu.person.entity.RoleEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author ?????
 * @email 2565477149@qq.com
 * @date 2022-09-21 21:36:01
 */
public interface RoleService extends IService<RoleEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<RoleEntity> listRoleById(int[] rids);

    RoleEntity getRoleAndPowerById(Integer id);

    List<List<Integer>> getRolePowerArray(Integer id);

    void saveRoleAndPower(RoleEntity role);

    void updateRoleAndPowerById(RoleEntity role);

    boolean removeRoleByIds(List<Integer> asList);
}

