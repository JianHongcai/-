package com.jian.usersenterback;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.jian.usersenterback.mapper")
public class UserSenterBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserSenterBackApplication.class, args);
    }

}
