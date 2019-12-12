package com.onepilltest.entity;

public class HeFeng {
    private String tem;//温度
    private String life;//生活指数
    private String more;

    public HeFeng(String tem,String life) {
        this.life = life;
        //this.more = more;
        this.tem = tem;
    }

    public String getTem() {
        return tem;
    }

    public void setTem(String tem) {
        this.tem = tem;
    }

    public String getLife() {
        return life;
    }

    public void setLife(String life) {
        this.life = life;
    }

    public String getMore() {
        return more;
    }

    public void setMore(String more) {
        this.more = more;
    }
}
