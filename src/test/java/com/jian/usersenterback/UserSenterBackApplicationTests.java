package com.jian.usersenterback;


import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

@SpringBootTest
class UserSenterBackApplicationTests {



    @Test
    void passWord(){
        final String Add = "junglesouljah";
        String newPassword = DigestUtils.md5DigestAsHex((Add + "abc").getBytes());
        System.out.println(newPassword);
    }

    @Test
    void contextLoads() {
    }





}
