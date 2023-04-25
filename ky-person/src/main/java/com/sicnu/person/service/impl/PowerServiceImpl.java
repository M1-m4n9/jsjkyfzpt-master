package com.sicnu.person.service.impl;

import com.sicnu.person.entity.RoleEntity;
import com.sicnu.person.entity.RolePowerEntity;
import com.sicnu.person.service.RolePowerService;
import com.sicnu.person.vo.MenuResponseVo;
import com.sicnu.person.vo.VueValueAndLabel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sicnu.common.utils.PageUtils;
import com.sicnu.common.utils.Query;

import com.sicnu.person.dao.PowerDao;
import com.sicnu.person.entity.PowerEntity;
import com.sicnu.person.service.PowerService;


@Service("powerService")
public class PowerServiceImpl extends ServiceImpl<PowerDao, PowerEntity> implements PowerService {
    @Autowired
    private RolePowerService rolePowerService;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String key = (String) params.get("key");
        QueryWrapper<PowerEntity> wrapper = new QueryWrapper<>();
        if(key!=null && !key.isEmpty() && key.length()>=1){
            wrapper.like("name",key);
        }
        IPage<PowerEntity> page = this.page(
                new Query<PowerEntity>().getPage(params),wrapper
        );

        return new PageUtils(page);
    }

    @Override
    public List<RoleEntity> getRolesByUrl(String url) {
        return baseMapper.getRolesByUrl(url);
    }

    @Override
    public void saveIfNo(PowerEntity power) {
        if(power == null) return;
        PowerEntity one = baseMapper.selectOne(new QueryWrapper<PowerEntity>().eq("url", power.getUrl()));
        if(one != null) return;
        baseMapper.insert(power);
    }


    /**
     * 获取侧边的菜单
     * @return
     */
    @Override
    public List<MenuResponseVo> getMenuAndUrl() {
        // 获取到旁边菜单需要展示的
        List<PowerEntity> powerEntities = baseMapper.selectList(new QueryWrapper<PowerEntity>().eq("is_show",1));

        List<MenuResponseVo> res = new ArrayList<>();
        // 一级菜单封装到res中
        powerEntities.stream().filter(power -> power.getParentId() == 0).distinct().forEach(power -> {
            // 一级菜单
            MenuResponseVo first = new MenuResponseVo();
            first.setPowerEntity(power);
            //二级菜单
            List<MenuResponseVo> second = powerEntities
                    .stream()
                    //二级菜单的parentId不等于0   二级菜单的url为空  二级菜单的父id 等于 一级菜单的id
                    .filter(power1 -> power1.getParentId() != 0 && power1.getUrl() == null || power1.getParentId() == power.getId())
                    .map(power12 -> {
                        MenuResponseVo vo = new MenuResponseVo();
                        vo.setPowerEntity(power12);
                        return vo;
                    })
                    .collect(Collectors.toList());
            first.setSonList(second);
            // 二级菜单的遍历
            second.stream().distinct().forEach(sec->{
                // 获取三级菜单 三级菜单的url 不为空 并且 三级菜单的parentId = 二级菜单的id
                List<MenuResponseVo> third = powerEntities
                        .stream()
                        .filter(power1 -> power1.getUrl() != null && power1.getParentId() == sec.getPowerEntity().getId())
                        .map(power1 -> {
                            MenuResponseVo vo = new MenuResponseVo();
                            vo.setPowerEntity(power1);
                            return vo;
                        })
                        .collect(Collectors.toList());
                sec.setSonList(third);
            });
            res.add(first);
        });

        return res;
    }

    /**
     * 获取全部的权限
     * @return
     */
    @Override
    public List<MenuResponseVo> getAllPower() {
        List<PowerEntity> powerEntities = baseMapper.selectList(null);

        List<MenuResponseVo> res = new ArrayList<>();
        // 一级菜单封装到res中
        powerEntities.stream().filter(power -> power.getParentId() == 0).distinct().forEach(power -> {
            // 一级菜单
            MenuResponseVo first = new MenuResponseVo();
            first.setPowerEntity(power);
            //二级菜单
            List<MenuResponseVo> second = powerEntities
                    .stream()
                    //二级菜单的parentId不等于0   二级菜单的url为空  二级菜单的父id 等于 一级菜单的id
                    .filter(power1 -> power1.getParentId() != 0 && power1.getUrl() == null || power1.getParentId() == power.getId())
                    .map(power12 -> {
                        MenuResponseVo vo = new MenuResponseVo();
                        vo.setPowerEntity(power12);
                        return vo;
                    })
                    .collect(Collectors.toList());
            first.setSonList(second);
            // 二级菜单的遍历
            second.stream().distinct().forEach(sec->{
                // 获取三级菜单 三级菜单的url 不为空 并且 三级菜单的parentId = 二级菜单的id
                List<MenuResponseVo> third = powerEntities
                        .stream()
                        .filter(power1 -> power1.getUrl() != null && power1.getParentId() == sec.getPowerEntity().getId())
                        .map(power1 -> {
                            MenuResponseVo vo = new MenuResponseVo();
                            vo.setPowerEntity(power1);
                            return vo;
                        })
                        .collect(Collectors.toList());
                sec.setSonList(third);
            });
            res.add(first);
        });
        return res;
    }


    /**
     * 为了适应vue的参数形式
     * @return
     */
    @Override
    public List<VueValueAndLabel> getVueValueAndLabel() {
        List<PowerEntity> powerEntities = baseMapper.selectList(null);

        List<VueValueAndLabel> res = new ArrayList<>();
        // 一级菜单封装到res中
        powerEntities.stream().filter(power -> power.getParentId() == 0).distinct().forEach(power -> {
            // 一级菜单
            VueValueAndLabel first = new VueValueAndLabel();
            first.setValue(power.getId());
            first.setLabel(power.getName());
            //二级菜单
            List<VueValueAndLabel> second = powerEntities
                    .stream()
                    //二级菜单的parentId不等于0   二级菜单的url为空  二级菜单的父id 等于 一级菜单的id
                    .filter(power1 -> power1.getParentId() != 0 && power1.getUrl() == null || power1.getParentId() == power.getId())
                    .map(power12 -> {
                        VueValueAndLabel vueValueAndLabel = new VueValueAndLabel();
                        vueValueAndLabel.setValue(power12.getId());
                        vueValueAndLabel.setLabel(power12.getName());
                        return vueValueAndLabel;
                    })
                    .collect(Collectors.toList());
            first.setChildren(second);
            // 二级菜单的遍历
            second.stream().distinct().forEach(sec->{
                // 获取三级菜单 三级菜单的url 不为空 并且 三级菜单的parentId = 二级菜单的id
                List<VueValueAndLabel> third = powerEntities
                        .stream()
                        .filter(power1 -> power1.getUrl() != null && power1.getParentId().intValue() == sec.getValue())
                        .map(power1 -> {
                            VueValueAndLabel vueValueAndLabel = new VueValueAndLabel();
                            vueValueAndLabel.setValue(power1.getId());
                            vueValueAndLabel.setLabel(power1.getName());
                            return vueValueAndLabel;
                        })
                        .collect(Collectors.toList());
                sec.setChildren(third);
            });
            res.add(first);
        });
        return res;
    }

    @Override
    public boolean removePowerByIds(List<Integer> asList) {
        //查询中间表查看当前是否有关联
        List<RolePowerEntity> rolePowerEntities = rolePowerService.list(new QueryWrapper<RolePowerEntity>().in("pid", asList));

        //不存在可以删
        if(rolePowerEntities == null || rolePowerEntities.size() == 0 || rolePowerEntities.isEmpty()){
            baseMapper.deleteBatchIds(asList);
            return true;
        }
        //存在不能删
        return false;
    }


}