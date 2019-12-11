package com.onepilltest.entity;

public class Article {
    private int id;
    private String headImg;
    private String writerName;
    private String content;
    private String tag;
    private String title;

    public Article() {
    }

    public Article(int id, String title, String articleCotent, String tag, String writerName) {
        this.id = id;
        this.writerName = writerName;
        this.content = articleCotent;
        this.tag = tag;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getWriterName() {
        return writerName;
    }

    public void setWriterName(String writerName) {
        this.writerName = writerName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String cotent) {
        this.content = cotent;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
