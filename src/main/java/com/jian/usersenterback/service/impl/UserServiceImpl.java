package com.jian.usersenterback.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jian.usersenterback.model.User;
import com.jian.usersenterback.service.UserService;
import com.jian.usersenterback.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
* @author 简宏才
* @description 针对表【user】的数据库操作Service实现
* @createDate 2024-04-15 16:42:54
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword) {
        //1.校验
        if(StringUtils.isAnyBlank(userAccount,userPassword,checkPassword)){
            return -1;
        }
        if(userAccount.length() < 4){
            return -1;
        }
        if(userPassword.length() < 8 || checkPassword.length() < 8){
            return -1;
        }

        //账户不能包含特殊字符
        String regex = ".*[^a-zA-Z0-9_].*";
        Matcher matcher = Pattern.compile(regex).matcher(userAccount);
        if( !matcher.find()){
            return -1;
        }

        //账户不能重复
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("userAccount",userAccount);
        long count = this.count(userQueryWrapper);
        if(count > 0){
            return -1;
        }

        //校验 用户密码和校验密码是否一样
        if(!userPassword.equals(checkPassword)){
            return -1;
        }

        //密码加密
        final String Add = "junglesouljah";
        String newPassword = DigestUtils.md5DigestAsHex((Add + userPassword).getBytes());

        //插入数据库
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(newPassword);
        boolean save = this.save(user);
        if( !save ){
            return -1;
        }

        return user.getId();
    }
}




