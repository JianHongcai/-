package com.jian.usersenterback.service.impl;


import com.jian.usersenterback.model.User;
import com.jian.usersenterback.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class UserServiceImplTest {

    @Resource
    private UserService userService;

    @Test
     void testAddUser(){
        User user = new User();
        user.setPhone("13595689644");
        user.setGender(0);
        user.setUserStatus(0);
        user.getIsDelete();
        boolean save = userService.save(user);
        System.out.println(user.getId());
        Assertions.assertTrue(save);

    }


    @Test
    void userRegister() {
        String userAccount = "jian";
        String userPassword = "";
        String checkPassword = "123456789";
        long result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1,result);
        userAccount = "jia";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1,result);
        userPassword = "1234";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1,result);
        userAccount = "jian";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1,result);
        userAccount = "ji a";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1,result);

    }
}