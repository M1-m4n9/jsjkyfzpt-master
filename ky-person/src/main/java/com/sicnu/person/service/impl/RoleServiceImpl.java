package com.sicnu.person.service.impl;

import com.sicnu.person.entity.PowerEntity;
import com.sicnu.person.entity.RolePowerEntity;
import com.sicnu.person.entity.UserRoleEntity;
import com.sicnu.person.service.PowerService;
import com.sicnu.person.service.RolePowerService;
import com.sicnu.person.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BulkBean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sicnu.common.utils.PageUtils;
import com.sicnu.common.utils.Query;

import com.sicnu.person.dao.RoleDao;
import com.sicnu.person.entity.RoleEntity;
import com.sicnu.person.service.RoleService;
import org.springframework.transaction.annotation.Transactional;


@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleDao, RoleEntity> implements RoleService {

    @Autowired
    private RolePowerService rolePowerService;
    @Autowired
    private PowerService powerService;
    @Autowired
    private UserRoleService userRoleService;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String key = (String)params.get("key");
        QueryWrapper<RoleEntity> wrapper = new QueryWrapper<>();
        if (key!=null&&!key.isEmpty())wrapper.like("name",key).or().like("introduction",key);
        IPage<RoleEntity> page = this.page(
                new Query<RoleEntity>().getPage(params),
                wrapper
        );

        return new PageUtils(page);
    }

    @Override
    public List<RoleEntity> listRoleById(int[] rids) {
        List<RoleEntity> roles = baseMapper.listRoleById(rids);
        return roles;
    }

    @Override
    public RoleEntity getRoleAndPowerById(Integer id) {
        // 查询出这个角色
        RoleEntity role = baseMapper.selectById(id);
        if(role == null){return null;}
        //查询出这个角色的权限
//        baseMapper.selectPowers(id);
        return role;
    }


    /**
     * 封装成二维度数组形式
     * @param id
     * @return
     */
    @Override
    public List<List<Integer>> getRolePowerArray(Integer id) {
        List<RolePowerEntity> rps = rolePowerService.list(new QueryWrapper<RolePowerEntity>().eq("rid", id));
        if(rps == null || rps.size() == 0 || rps.isEmpty()) return null;
        List<Integer> pids = rps.stream().map(res -> (Integer) res.getPid()).collect(Collectors.toList());

        // 查出了三级
        List<PowerEntity> first = powerService.listByIds(pids);
        // 二级就是first的parent
        List<Integer> se = first.stream().map(res -> (Integer) res.getParentId()).collect(Collectors.toList());
        List<PowerEntity> second = powerService.listByIds(se);

        List<List<Integer>> res = new ArrayList<>();
        first.stream().forEach(fir->{
            ArrayList<Integer> temp = new ArrayList<>();
            second.stream().filter(s->fir.getParentId().intValue()==s.getId()).forEach(sec->{
                temp.add(sec.getParentId());
                temp.add(sec.getId());
                temp.add(fir.getId());
            });
            res.add(temp);
        });
        return res;
    }


    /**
     * 使用事务操作两张表
     * @param role
     */
    @Transactional
    @Override
    public void saveRoleAndPower(RoleEntity role) {
        // 插入角色表
        baseMapper.insert(role);
        List<List<Integer>> value = role.getValue();
        List<RolePowerEntity> rpList = new ArrayList<>();
        value.stream().forEach(list->{
            RolePowerEntity rp = new RolePowerEntity();
            rp.setRid(role.getId());
            rp.setPid(list.get(2));
            rpList.add(rp);
        });
        // 插入角色权限表
        rolePowerService.saveBatch(rpList);
    }

    /**
     * 修改角色
     * @param role
     */
    @Transactional
    @Override
    public void updateRoleAndPowerById(RoleEntity role) {
        //先修改角色表
        baseMapper.updateById(role);
        //中间表先删除
        rolePowerService.remove(new QueryWrapper<RolePowerEntity>().eq("rid",role.getId()));
        //中间表再添加
        List<List<Integer>> value = role.getValue();
        List<RolePowerEntity> rpList = new ArrayList<>();
        value.stream().forEach(list->{
            RolePowerEntity rp = new RolePowerEntity();
            rp.setRid(role.getId());
            rp.setPid(list.get(2));
            rpList.add(rp);
        });
        // 插入角色权限表
        rolePowerService.saveBatch(rpList);
    }

    @Transactional
    @Override
    public boolean removeRoleByIds(List<Integer> asList) {
        if(asList == null || asList.size() == 0 || asList.isEmpty()){
            return true;
        }
        QueryWrapper<UserRoleEntity> wrapper = new QueryWrapper<>();
        wrapper.in("rid",asList);
        List<UserRoleEntity> list = userRoleService.list(wrapper);
        if(list == null || list.size() == 0 || list.isEmpty()){
            //说明该角色没有被关联，那么就可以删除
            baseMapper.deleteBatchIds(asList);
            return true;
        }else{
            //说明角色有关联，不能删除
            return false;
        }
    }

}