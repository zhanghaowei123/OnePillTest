package com.onepilltest.entity;

public class Comment {
    private String name;
    private String ccomment;
    private String headImg;

    public Comment() {
    }

    public Comment(String name, String ccomment) {
        this.name = name;
        this.ccomment = ccomment;
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
}
