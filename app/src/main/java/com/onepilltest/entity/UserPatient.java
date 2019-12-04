package com.onepilltest.entity;

public class UserPatient {
    private String userId;
    private String nickName;
    private String phone;
    private String password;
    private String PID;
    private String address;
    private String headImg;//图片在服务器端的相对路径

    public UserPatient() {
    }

    public UserPatient(String nickName, String phone,
                       String password, String PID,
                       String address, String headImg) {
        this.nickName = nickName;
        this.phone = phone;
        this.password = password;
        this.PID = PID;
        this.address = address;
        this.headImg = headImg;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPID() {
        return PID;
    }

    public void setPID(String PID) {
        this.PID = PID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    @Override
    public String toString() {
        return "UserPatient{" +
                "userId='" + userId + '\'' +
                ", nickName='" + nickName + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", PID='" + PID + '\'' +
                ", address='" + address + '\'' +
                ", headImg='" + headImg + '\'' +
                '}';
    }
}
