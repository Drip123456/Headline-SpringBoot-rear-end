package com.drip.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.drip.pojo.User;
import com.drip.service.UserService;
import com.drip.mapper.UserMapper;
import com.drip.utils.JwtHelper;
import com.drip.utils.MD5Util;
import com.drip.utils.Result;
import com.drip.utils.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;
import java.util.Map;

/**
 * @author qq316
 * @description 针对表【news_user】的数据库操作Service实现
 * @createDate 2024-05-02 15:54:39
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtHelper jwtHelper;

    @Override
    public Result login(User user) {
        //根据账号查询
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername,user.getUsername());
        User loginUser = userMapper.selectOne(queryWrapper);

        //账号判断
        if (loginUser == null) {
            //账号错误
            return Result.build(null, ResultCodeEnum.USERNAME_ERROR);
        }

        //判断密码
        if (!StringUtils.isEmpty(user.getUserPwd())
                && loginUser.getUserPwd().equals(MD5Util.encrypt(user.getUserPwd())))
        {
            //账号密码正确
            //根据用户唯一标识生成token
            String token = jwtHelper.createToken(Long.valueOf(loginUser.getUid()));

            Map data = new HashMap();
            data.put("token",token);

            return Result.ok(data);
        }

        //密码错误
        return Result.build(null,ResultCodeEnum.PASSWORD_ERROR);
    }

    /**
     * 1 校验token有效性
     * 2 根据token查找uid
     * 3 根据uid查找user
     * @param token
     * @return
     */
    @Override
    public Result getUserInfo(String token) {
        boolean expiration = jwtHelper.isExpiration(token);
        if(!expiration){
            Long userId = jwtHelper.getUserId(token);
            User user = userMapper.selectById(userId);
            user.setUserPwd("");
            Map data=new HashMap();
            data.put("loginUser",user);
            return Result.ok(data);
        }
        return Result.build(null,ResultCodeEnum.NOTLOGIN);
    }

    /**
     * 1.根据username查询数据
     * 2.如果存在即被占用
     * @param username
     * @return
     */
    @Override
    public Result checkUserName(String username) {
        LambdaQueryWrapper<User> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUsername,username);
        User user = userMapper.selectOne(lambdaQueryWrapper);
        if(user!=null){
            return Result.build(null,ResultCodeEnum.USERNAME_USED);
        }
        return Result.ok(null);
    }

    @PostMapping("regist")
    @Override
    public Result regist(User user) {
        LambdaQueryWrapper<User> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUsername,user.getUsername());
        User user1 = userMapper.selectOne(lambdaQueryWrapper);
        if(user1!=null){
            return Result.build(null,ResultCodeEnum.USERNAME_USED);
        }
            user.setUserPwd(MD5Util.encrypt(user.getUserPwd()));
            userMapper.insert(user);
            return Result.ok(null);
    }
}




