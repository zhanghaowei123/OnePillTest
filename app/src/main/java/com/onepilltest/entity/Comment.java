package com.onepilltest.entity;

public class Comment {
    private int id;
    private String name;
    private String ccomment;
    private String headImg;
    private int articleId;
    private int isGood;
    private int isBad;
    private int goodNum;
    private int badNum;

    public Comment() {
    }

    public Comment(String name, String ccomment) {
        this.name = name;
        this.ccomment = ccomment;
    }

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

    public String getCcomment() {
        return ccomment;
    }

    public void setCcomment(String ccomment) {
        this.ccomment = ccomment;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public int getIsGood() {
        return isGood;
    }

    public void setIsGood(int isGood) {
        this.isGood = isGood;
    }

    public int getIsBad() {
        return isBad;
    }

    public void setIsBad(int isBad) {
        this.isBad = isBad;
    }

    public int getGoodNum() {
        return goodNum;
    }

    public void setGoodNum(int goodNum) {
        this.goodNum = goodNum;
    }

    public int getBadNum() {
        return badNum;
    }

    public void setBadNum(int badNum) {
        this.badNum = badNum;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "name='" + name + '\'' +
                ", ccomment='" + ccomment + '\'' +
                ", headImg='" + headImg + '\'' +
                ", articleId=" + articleId +
                ", isGood=" + isGood +
                ", isBad=" + isBad +
                ", goodNum=" + goodNum +
                ", badNum=" + badNum +
                '}';
    }
}
