package com.onepilltest.entity;

public class Orders {
    private int id;
    private int userId;
    private int medicineId;
    private int count;//数量
    private String img;
    private int price;
    private int status;


    public Orders() {

    }


    public Orders(int id, int userId, int medicineId, int count, String img, int price, int status) {
        this.id = id;
        this.userId = userId;
        this.medicineId = medicineId;
        this.count = count;
        this.img = img;
        this.price = price;
        this.status = status;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(int medicineId) {
        this.medicineId = medicineId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
