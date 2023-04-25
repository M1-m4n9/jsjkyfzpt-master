package com.sicnu.person.service.impl;

import com.sicnu.common.exception.CodeEnume;
import com.sicnu.common.exception.KyException;
import com.sicnu.common.utils.CheckEmailOrPhone;
import com.sicnu.common.utils.MD5Utils;
import com.sicnu.person.entity.RoleEntity;
import com.sicnu.person.entity.UserRoleEntity;
import com.sicnu.person.service.RoleService;
import com.sicnu.person.service.UserRoleService;
import com.sicnu.person.vo.UserCenterVo;
import com.sicnu.person.vo.UserLoginVo;
import com.sicnu.person.vo.UserRegistVo;
import com.sicnu.person.vo.UserResponseVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sicnu.common.utils.PageUtils;
import com.sicnu.common.utils.Query;

import com.sicnu.person.dao.UserDao;
import com.sicnu.person.entity.UserEntity;
import com.sicnu.person.service.UserService;
import org.springframework.transaction.annotation.Transactional;


@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {


    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private RoleService roleService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String type = (String) params.get("type");
        System.out.println(type);
        QueryWrapper<UserEntity> wrapper = new QueryWrapper<>();
        //type == 0 说明是查普通用户
        if(Integer.valueOf(type) == 0){
//            wrapper.eq("type",0).or().eq("type",1);
            wrapper.or(i->i.eq("type",0).or().eq("type",1));
        }
        //type == 1 说明是查管理员
        if(Integer.valueOf(type)== 1){
            wrapper.eq("type",-1);
        }

        //判断有没有查询条件
        String key = (String)params.get("key");
        if(key != null && !key.isEmpty() && key.length() >= 1){
            wrapper.and(i->i.like("username",key).or().like("school_name",key));
        }
        IPage<UserEntity> page = this.page(
                new Query<UserEntity>().getPage(params),
                wrapper
        );
        // 获取到了所有的记录
        List<UserEntity> list = page.getRecords();
        //获取所有的用户id
        List<Integer> uids = list.stream().map(u -> (Integer) u.getId()).collect(Collectors.toList());
        //查询所有用户的所有角色
        List<UserRoleEntity> userRoleEntities = userRoleService.list(new QueryWrapper<UserRoleEntity>().in("uid", uids));
        // 获取所有的角色id
        List<Integer> rids = userRoleEntities.stream().map(ur -> (Integer) ur.getRid()).collect(Collectors.toList());
        // 获取所有的角色
        List<RoleEntity> roles = roleService.listByIds(rids);
        //封装
        list.stream().forEach(user->{
            List<Integer> urids = userRoleEntities.stream()
                    .filter(ur -> ur.getUid().intValue() == user.getId())
                    .map(ur -> (Integer) ur.getRid())
                    .collect(Collectors.toList());
            List<RoleEntity> uroles = roles.stream().filter(role -> urids.contains(role.getId())).distinct().collect(Collectors.toList());
            user.setRoles(uroles);
        });
        page.setRecords(list);
        return new PageUtils(page);
    }

    @Override
    public UserResponseVo login(UserLoginVo userLoginVo) {
        String loginAcct = userLoginVo.getLoginAcct();
        String password = userLoginVo.getPassword();
        QueryWrapper<UserEntity> wrapper = new QueryWrapper<>();
        // 判断是那种格式账号登录(手机号 / 邮箱)
        if(CheckEmailOrPhone.checkMobileNumber(loginAcct)){
            wrapper.eq("phone", loginAcct);
        }else if(CheckEmailOrPhone.checkEmail(loginAcct)){
            wrapper.eq("email",loginAcct);
        }else{
            throw new KyException(CodeEnume.USER_ACCOUNT_TYPE_ERROR);
        }
        UserEntity user = baseMapper.selectOne(wrapper);
        // 没有这个用户
        if(user == null){
            throw new KyException(CodeEnume.USER_NULL);
        }
        //该用户被冻结
        if(user.getStatus().intValue() == 1){
            throw new KyException(CodeEnume.USER_NOT_ACTIVE);
        }
        //账号或密码不匹配
        if(!user.getPassword().equals(password)){
            throw new KyException(CodeEnume.USER_NOT_MATCH);
        }
        UserResponseVo responseVo = new UserResponseVo();
        BeanUtils.copyProperties(user,responseVo);
        return responseVo;
    }

    @Override
    public UserEntity getUserByPhoneOrEmail(String loginAcct) {
        QueryWrapper<UserEntity> wrapper = new QueryWrapper<>();
        // 判断是那种格式账号登录(手机号 / 邮箱)
        if(CheckEmailOrPhone.checkMobileNumber(loginAcct)){
            wrapper.eq("phone", loginAcct);
        }else if(CheckEmailOrPhone.checkEmail(loginAcct)){
            wrapper.eq("email",loginAcct);
        }else{
            throw new KyException(CodeEnume.USER_ACCOUNT_TYPE_ERROR);
        }
        UserEntity user = baseMapper.selectOne(wrapper);
        // 没有这个用户
        if(user == null){
            throw new KyException(CodeEnume.USER_NULL);
        }
        //该用户被冻结
        if(user.getStatus().intValue() == 1){
            throw new KyException(CodeEnume.USER_NOT_ACTIVE);
        }
        //查询出这个用户的角色
        List<UserRoleEntity> userRoleEntities = userRoleService.list(new QueryWrapper<UserRoleEntity>().eq("uid", user.getId()));
        if(userRoleEntities == null || userRoleEntities.isEmpty()){
            throw new KyException(CodeEnume.USER_NO_ROLE);
        }
        HashSet<Integer> roleIds = new HashSet<>();
        userRoleEntities.stream().forEach(u->roleIds.add(u.getRid()));
        List<RoleEntity> roles = roleService.listByIds(roleIds);
        user.setRoles(roles);
        return user;
    }

    @Override
    public UserEntity getUserAndRoleById(Integer id) {
        UserEntity user = baseMapper.selectById(id);
        List<UserRoleEntity> urs = userRoleService.list(new QueryWrapper<UserRoleEntity>().eq("uid", id));
        // 获取这个用户对应的角色id
        List<Integer> rids = urs.stream().map(u -> (Integer) u.getRid()).collect(Collectors.toList());
        if(rids == null || rids.isEmpty() || rids.size() == 0) return user;
        List<RoleEntity> roles = roleService.listByIds(rids);
        user.setRoles(roles);
        return user;
    }

    @Transactional
    @Override
    public void updateUserAndUserRoleById(UserEntity user) {
        // 先更新user表
        baseMapper.updateById(user);
        // 再删除中间表
        userRoleService.remove(new QueryWrapper<UserRoleEntity>().eq("uid",user.getId()));
        // 再添加中间表
        List<Integer> values = user.getValues();
        if(values == null || values.isEmpty() || values.size() == 0){return;}
        List<UserRoleEntity> list = new ArrayList<>();
        values.stream().forEach(v->{
            UserRoleEntity userRole = new UserRoleEntity();
            userRole.setUid(user.getId());
            userRole.setRid(v);
            list.add(userRole);
        });
        userRoleService.saveBatch(list);
    }

    @Transactional
    @Override
    public void saveUserAndUserRole(UserEntity user) {
        //先保存User
        baseMapper.insert(user);
        //再插入中间表
        List<Integer> values = user.getValues();
        if(values == null || values.isEmpty() || values.size() == 0){
            return;
        }
        List<UserRoleEntity> list = new ArrayList<>();
        values.stream().forEach(v->{
            UserRoleEntity userRole = new UserRoleEntity();
            userRole.setUid(user.getId());
            userRole.setRid(v);
            list.add(userRole);
        });
        userRoleService.saveBatch(list);
    }
    
    @Override
    public UserCenterVo getSimpleInfoById(Integer id)
    {
        UserEntity userEntity = baseMapper.selectById(id);
        UserCenterVo userCenterVo = new UserCenterVo();
        BeanUtils.copyProperties(userEntity, userCenterVo);
        return userCenterVo;
    }

    @Transactional
    @Override
    public void register(UserRegistVo userRegistVo) {
        //1.注册用户
        UserEntity user = new UserEntity();
        user.setUsername(userRegistVo.getUsername());
        user.setPassword(MD5Utils.MD5(userRegistVo.getPassword1()));
        user.setSex(0);
        user.setStatus(0);
        user.setType(0);
        user.setBirthday(new Date());
        user.setPhone(userRegistVo.getPhone());
        user.setIntroduction("这个人很懒,什么也没有写...");
        user.setFansCount(0);
        user.setFollowCount(0);
        user.setVisitCount(0);
        user.setMajorName("没填专业");
        user.setSchoolName("门头沟大学");
        baseMapper.insert(user);

        //2.改用户的角色
        UserRoleEntity userRole = new UserRoleEntity();
        userRole.setUid(user.getId());
        userRole.setRid(2);
        userRoleService.save(userRole);
    }

}