package com.onepilltest.personal;

//交易记录
public class WalletBase  {
    private boolean code;//交易状态:true:+   false:-

    private String time = null;//交易时间
    private String cash = null;//交易金额

    public WalletBase(String time,String cash,boolean code){
        this.cash = cash;
        this.time = time;
        this.code = code;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCash() {
        return cash;
    }

    public void setCash(String cash) {
        this.cash = cash;
    }

    public boolean isCode() {
        return code;
    }

    public void setCode(boolean code) {
        this.code = code;
    }
}
