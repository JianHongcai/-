package com.jian.usersenterback.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jian.usersenterback.model.User;
import com.jian.usersenterback.service.UserService;
import com.jian.usersenterback.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.jian.usersenterback.contant.UserConstant.USER_LOGIN_STATE;

/**
* @author 简宏才
* @description 针对表【user】的数据库操作Service实现
* @createDate 2024-04-15 16:42:54
*/
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    /**
     * 盐值，混淆密码
     */
    private static final String Add = "junglesouljah";


    @Autowired
    private UserMapper userMapper;

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

    @Override
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        //1.校验
        if(StringUtils.isAnyBlank(userAccount,userPassword)){
            return null;
        }
        if(userAccount.length() < 4){
            return null;
        }
        if(userPassword.length() < 8 ){
            return null;
        }

        //账户不能包含特殊字符
        String regex = ".*[^a-zA-Z0-9_].*";
        Matcher matcher = Pattern.compile(regex).matcher(userAccount);
        if( matcher.find()){
            return null;
        }

        //密码加密
        String encryptPassword = DigestUtils.md5DigestAsHex((Add + userPassword).getBytes());
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.lambda().eq(User::getUserAccount, userAccount)
                .eq(User::getUserPassword, encryptPassword);
//        userQueryWrapper.eq("userAccount",userAccount);
//        userQueryWrapper.eq("userPassword",userPassword);
        User user = userMapper.selectOne(userQueryWrapper);
        if(user == null){
//            log.info("user login failed,userAccount cannot match userPassword");
//            log.error("User not found with userAccount: {} and encrypted password: {}", userAccount, encryptPassword);
            return null;
        }
            User safetyUser = getSafetyUser(user);
        //记录用户的登陆状态
        request.getSession().setAttribute(USER_LOGIN_STATE,safetyUser);
        return safetyUser;
    }

    /**
     * 用户脱敏
     * @param originUser
     * @return User
     *
     */
    @Override
    public User getSafetyUser(User originUser){
        //
        User safetyUser = new User();
        safetyUser.setId(originUser.getId());
        safetyUser.setUsername(originUser.getUsername());
        safetyUser.setUserRole(originUser.getUserRole());
        safetyUser.setUserAccount(originUser.getUserAccount());
        safetyUser.setAvatarUrl(originUser.getAvatarUrl());
        safetyUser.setGender(originUser.getGender());
        safetyUser.setPhone(originUser.getPhone());
        safetyUser.setEmail(originUser.getEmail());
        safetyUser.setUserStatus(originUser.getUserStatus());
        safetyUser.setCreateTime(new Date());

        return safetyUser;
    }



}




