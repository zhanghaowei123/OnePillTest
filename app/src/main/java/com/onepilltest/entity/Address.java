package com.onepilltest.entity;

public class Address {
    private int userId;
    private int id;
    private String name;
    private String phoneNumber;
    private String address;
    private String more;
    private String postalCode;

    public Address(int UserId, String name, String phoneNumber, String address, String more, String postalCode) {
        this.userId = UserId;
        this.id = 0;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.more = more;
        this.postalCode = postalCode;
    }

    public Address(int id, int UserId, String name, String phoneNumber, String address, String more, String postalCode) {
        this.userId = UserId;
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.more = more;
        this.postalCode = postalCode;
    }
    public Address(){
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMore() {
        return more;
    }

    public void setMore(String more) {
        this.more = more;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String toString() {
        return "Id" + getId() + "\nUserId" + getUserId() + "\nname" + getName() + "\nphoneNumber" + getPhoneNumber() + "\nAddress" + getAddress() + "\nmore" + getMore() + "\npostalCode" + getPostalCode();

    }
}
