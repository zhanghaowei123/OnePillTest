package com.onepilltest.entity;

public class Comment {
    private String name;
    private String ccomment;
    private String headImg;
    private int articleId;

    public Comment() {
    }

    public Comment(String name, String ccomment) {
        this.name = name;
        this.ccomment = ccomment;
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

    @Override
    public String toString() {
        return "Comment{" +
                "name='" + name + '\'' +
                ", ccomment='" + ccomment + '\'' +
                '}';
    }
}
