package com.sicnu.person.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sicnu.common.utils.PageUtils;
import com.sicnu.person.entity.UserEntity;
import com.sicnu.person.vo.UserCenterVo;
import com.sicnu.person.vo.UserLoginVo;
import com.sicnu.person.vo.UserRegistVo;
import com.sicnu.person.vo.UserResponseVo;

import java.util.Map;

/**
 * 
 *
 * @author ?????
 * @email 2565477149@qq.com
 * @date 2022-09-21 21:36:01
 */
public interface UserService extends IService<UserEntity> {

    PageUtils queryPage(Map<String, Object> params);

    UserResponseVo login(UserLoginVo userLoginVo);

    UserEntity getUserByPhoneOrEmail(String s);

    UserEntity getUserAndRoleById(Integer id);

    void updateUserAndUserRoleById(UserEntity user);

    void saveUserAndUserRole(UserEntity user);
	
	UserCenterVo getSimpleInfoById(Integer id);

    void register(UserRegistVo userRegistVo);
}

