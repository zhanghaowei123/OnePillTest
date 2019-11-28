package com.onepilltest.entity;

public class Result {
    private UserPatient user;
    private int code; //1.登录成功 2.电话不存在 3.密码错误

    public UserPatient getUser() {
        return user;
    }

    public void setUser(UserPatient user) {
        this.user = user;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


}	
