package com.onepilltest.entity;

public class ToFocus {
        private int id;//Id自增
        private int userId;//用户ID
        private int userType;//用户类型（1：医生2：用户）
        private int type;//关注类型（1：医生2：药品）
        private int typeId;//关注对象Id
        private String img;//展示图片
        private String name;//名称
        private String more;//介绍

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMore() {
        return more;
    }

    public void setMore(String more) {
        this.more = more;
    }

    @Override
    public String toString() {
        return "ToFocus{" +
                "id=" + id +
                ", userId=" + userId +
                ", userType=" + userType +
                ", type=" + type +
                ", typeId=" + typeId +
                ", img='" + img + '\'' +
                ", name='" + name + '\'' +
                ", more='" + more + '\'' +
                '}';
    }
}
