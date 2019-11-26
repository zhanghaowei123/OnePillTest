package com.onepilltest.index;

public class Article {
    private String img;
    private String writerName;
    private String articleCotent;
    private String tag;
    private String comment;

    public Article() {
    }

    public Article(String writerName, String articleCotent, String tag, String comment) {
        this.writerName = writerName;
        this.articleCotent = articleCotent;
        this.tag = tag;
        this.comment = comment;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getWriterName() {
        return writerName;
    }

    public void setWriterName(String writerName) {
        this.writerName = writerName;
    }

    public String getArticleCotent() {
        return articleCotent;
    }

    public void setArticleCotent(String articleCotent) {
        this.articleCotent = articleCotent;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
