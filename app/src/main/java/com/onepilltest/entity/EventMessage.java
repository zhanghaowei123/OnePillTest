package com.onepilltest.entity;

public class EventMessage {
    private String code;//指定什么操作
    /**
     * 1.AddressDao_save：保存地址；re：yes\no
     * 2.AddressDao_searchAll：搜索全部地址；re：List<Address>
     */
    private String json;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }
}
