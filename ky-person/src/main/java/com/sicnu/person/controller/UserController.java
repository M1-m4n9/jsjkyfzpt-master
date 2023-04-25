package com.sicnu.person.controller;

import java.util.Arrays;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sicnu.common.utils.*;
import com.sicnu.person.config.RsaKeyProperties;
import com.sicnu.person.service.PowerService;
import com.sicnu.person.vo.UserCenterVo;
import com.sicnu.person.vo.UserLoginVo;
import com.sicnu.person.vo.UserRegistVo;
import com.sicnu.person.vo.UserResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.sicnu.person.entity.UserEntity;
import com.sicnu.person.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 * 
 *
 * @author ?????
 * @email 2565477149@qq.com
 * @date 2022-09-21 21:36:01
 */
@RestController
@RequestMapping("person/user")
public class UserController {

    @Autowired
    private RsaKeyProperties prop;
    @Autowired
    private UserService userService;


    @PostMapping("/update")
    public R updateUser(@RequestBody UserEntity userEntity){
        userService.updateById(userEntity);
        return R.ok();
    }

    @GetMapping("/details")
    public R getUserInfo(HttpServletRequest request){
        String token = request.getHeader(Constant.AUTH_TOKEN_HEADER_NAME);
        if(token == null || token == "" || token.isEmpty()) return R.error().put("data","没有登录");
        Payload<UserEntity> payload = JwtUtils.getInfoFromToken(token.replace(Constant.AUTH_TOKEN_HEADER_RESOURCE_START, ""), prop.getPublicKey(), UserEntity.class);
        UserEntity user = userService.getUserByPhoneOrEmail(payload.getUserInfo().getUsername());
        user.setPassword(null);
        return R.ok().put("data",user);
    }

    /**
     * 登录认证
     * @param userLoginVo
     * @return
     */
    @PostMapping("/login")
    public R login(@RequestBody UserLoginVo userLoginVo){
        UserResponseVo responseVo = userService.login(userLoginVo);
        return R.ok().put("data",responseVo);
    }

    @PostMapping("/getUserByName")
    public UserEntity getUserByName(@RequestBody String s){
        UserEntity user = userService.getUserByPhoneOrEmail(s);
        return user;
    }

    @RequestMapping("/sendCode")
    public R sendCode(@RequestParam String phone){
        System.out.println(phone);
        SMSUtils.sendMessage(phone,"1400718699","怪人友OL公众号","1500982","1234");
        return R.ok();
    }

    @PostMapping("/register")
    public R register(@RequestBody UserRegistVo userRegistVo){
        //1.判断两次密码是否一样
        if(userRegistVo.getPassword1() == null || userRegistVo.getPassword2() == null ||
        !userRegistVo.getPassword1().equals(userRegistVo.getPassword2())){
            return R.error().put("data","两次密码不一致");
        }
        //2.判断验证码是否正确
        // 太贵了,买不起短信服务 不买了哈哈哈哈

        //3.判断手机号是否被注册
        UserEntity phone = userService.getOne(new QueryWrapper<UserEntity>().eq("phone", userRegistVo.getPhone()));
        if(phone != null) {
            return R.error().put("data","该手机号已经被注册");
        }
        //4.注册
        userService.register(userRegistVo);
        return R.ok().put("data","注册成功");
    }


    @GetMapping("/test")
    public UserEntity test(){
        UserEntity user = new UserEntity();
        user.setMajorName("aaaa");
        return user;
    }



    @GetMapping("/")
    public R test(HttpServletRequest request){
        HttpSession session = request.getSession();
        Object loginuser = session.getAttribute("loginuser");
        System.out.println(loginuser);
        return (R) loginuser;
    }

    @PostMapping("/logout")
    public R logout(){
        return R.ok().put("data","退出成功");
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = userService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info")
    public R info(@RequestParam Integer id){
		UserEntity user = userService.getUserAndRoleById(id);

        return R.ok().put("user", user);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody UserEntity user){
		userService.saveUserAndUserRole(user);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody UserEntity user){
		userService.updateUserAndUserRoleById(user);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids){
        boolean b = userService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }
    
    
    /**
     * 个人中心获取单个用户部分信息
     */
    @GetMapping("/info/simple")
    public R simpleInfo(@RequestParam Integer id)
    {
        UserEntity user = userService.getById(id);
        UserCenterVo userCenterVo = userService.getSimpleInfoById(id);
        return R.ok().put("simpleUser", userCenterVo);
    }

}
