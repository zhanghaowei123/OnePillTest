package com.onepilltest.entity;

public class UserDoctor {
    private String DoctorId;
    private String name;
    private String phone;
    private String address;
    private String password;
    private String PID;
    private String hospital;
    private String licence1;    //医师资格证的正面的地址，在手机上传
    private String licence2;    //医师资格证的反面的地址，在手机上传
    private String headImg;//系统有一个默认的头像在服务器端

    public String getDoctorId() {
        return DoctorId;
    }

    public void setDoctorId(String doctorId) {
        DoctorId = doctorId;
    }

    public UserDoctor() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getLicence1() {
        return licence1;
    }

    public void setLicence1(String licence1) {
        this.licence1 = licence1;
    }

    public String getLicence2() {
        return licence2;
    }

    public void setLicence2(String licence2) {
        this.licence2 = licence2;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    @Override
    public String toString() {
        return "UserDoctor{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", password='" + password + '\'' +
                ", PID='" + PID + '\'' +
                ", hospital='" + hospital + '\'' +
                '}';
    }
}
