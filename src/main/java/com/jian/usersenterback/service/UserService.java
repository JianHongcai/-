package com.jian.usersenterback.service;

import com.jian.usersenterback.model.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 简宏才
* @description 针对表【user】的数据库操作Service
* @createDate 2024-04-15 16:42:54
*/
public interface UserService extends IService<User> {

    /**
     * 用户注册
     * @param userAccount 账号
     * @param userPassword 密码
     * @param checkPassword 校验密码
     * @return 新用户ID
     */
  long userRegister(String userAccount,String userPassword,String checkPassword);
}
