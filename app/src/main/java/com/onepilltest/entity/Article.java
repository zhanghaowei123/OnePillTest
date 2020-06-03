package com.onepilltest.entity;

public class Article {
    private int id;
    private String headImg;
    private String writerName;
    private String content;
    private String tag;
    private String title;
    private int userId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setContent(String content) {
        this.content = content;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "ToArticle{" +
                "id=" + id +
                ", headImg='" + headImg + '\'' +
                ", writerName='" + writerName + '\'' +
                ", content='" + content + '\'' +
                ", tag='" + tag + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
