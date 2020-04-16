package com.onepilltest.entity;

//接收服务器返回的购物车
public class MyCart {
    private int id;        //订单Id
    private int userId;    //用户Id
    private int medicineId;//药品Id
    private int count;     //数量
    private int price;     //价格
    private int status;    //状态
    private String img;    //商品图片
    private String name;   //药品名
    private String standard;//规格

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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }


    @Override
    public String toString() {
        return "MyCart{" +
                "id=" + id +
                ", userId=" + userId +
                ", medicineId=" + medicineId +
                ", count=" + count +
                ", price=" + price +
                ", status=" + status +
                ", img='" + img + '\'' +
                ", name='" + name + '\'' +
                ", standard='" + standard + '\'' +
                '}';
    }
}
