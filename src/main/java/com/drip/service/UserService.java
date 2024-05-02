package com.drip.service;

import com.drip.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.drip.utils.Result;

/**
* @author qq316
* @description 针对表【news_user】的数据库操作Service
* @createDate 2024-05-02 15:54:39
*/
public interface UserService extends IService<User> {

    /**
     * 登录业务
     * @param user
     * @return
     */
    Result login(User user);

    Result getUserInfo(String token);

    Result checkUserName(String username);

    Result regist(User user);
}
