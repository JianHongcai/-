package com.jian.usersenterback.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户登陆请求体
 * @author 简宏才
 */
@Data
public class UserLoginRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    public String userAccount;

    public String userPassword;
}
