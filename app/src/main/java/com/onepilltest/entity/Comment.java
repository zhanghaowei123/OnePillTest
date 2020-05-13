package com.onepilltest.entity;

public class Comment {
    private int id;
    private int articleId;
    private int userId;
    private int userType;
    private String name;
    private String headImg;
    private String ccomment;
    private int goodNum;
    private boolean isGood;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getCcomment() {
        return ccomment;
    }

    public void setCcomment(String ccomment) {
        this.ccomment = ccomment;
    }

    public int getGoodNum() {
        return goodNum;
    }

    public void setGoodNum(int goodNum) {
        this.goodNum = goodNum;
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

    public boolean isGood() {
        return isGood;
    }

    public void setGood(boolean good) {
        isGood = good;
    }

    @Override
    public String toString() {
        return "ToComment{" +
                "id=" + id +
                ", articleId=" + articleId +
                ", userId=" + userId +
                ", userType=" + userType +
                ", name='" + name + '\'' +
                ", headImg='" + headImg + '\'' +
                ", ccomment='" + ccomment + '\'' +
                ", goodNum=" + goodNum +
                ", isGood=" + isGood +
                '}';
    }
}

