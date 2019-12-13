package com.onepilltest.entity;


public class Inquiry {
    private int id;
    private int userId;
    private String title;
    private String content;
    private String img;
    private String time;
    private int flag;

    public Inquiry() {
    }

    public Inquiry(int userId, String title, String content, String doctor_id, String flag, String img, String time) {
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.img = img;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getImg() {
        return img;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "Inquiry{" +
                "UserId=" + userId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", img='" + img + '\'' +
                '}';
    }
}
