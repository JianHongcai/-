package com.jian.usersenterback.service.impl;


import com.jian.usersenterback.model.User;
import com.jian.usersenterback.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;

@SpringBootTest
class UserServiceImplTest {

    @Resource
    private UserService userService;

    @Test
     void testAddUser(){
        final String Add = "junglesouljah";
        String newPassword = DigestUtils.md5DigestAsHex((Add + "123456789").getBytes());
        User user = new User();
        user.setUserAccount("hong");
        user.setPhone("13595689644");
        user.setUserPassword(newPassword);
        user.setGender(0);
        user.setUserStatus(0);
        boolean save = userService.save(user);
        System.out.println(user.getId());
        Assertions.assertTrue(save);

    }


    @Test
    void userRegister() {
        String userAccount = "jian";
        String userPassword = "123456789";
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

    @Test
    void userLogin() {
        String userAccount = "jian";
        String userPassword = "123456789";



    }
}