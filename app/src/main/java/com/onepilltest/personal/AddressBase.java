package com.onepilltest.personal;

import com.onepilltest.entity.Address;

/**
 * 用户地址数据
 */
public class AddressBase {
    private int ImgId;//用户头像资源地址
    private Address address;

    public AddressBase(int imgId, Address address) {
        ImgId = imgId;
        this.address = address;
    }

    public int getImgId() {
        return ImgId;
    }

    public void setImgId(int imgId) {
        ImgId = imgId;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }


}
