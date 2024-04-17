package com.jian.usersenterback.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册请求体
 * @author 简宏才
 */

@Data
public class UserRegisterRequest implements Serializable {

   private static final long serialVersionUID = 1L;

   public String userAccount;

   public String userPassword;

   public String checkPassword;
}
