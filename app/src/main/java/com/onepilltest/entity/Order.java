package com.onepilltest.entity;

public class Order {
    public static final int TYPE_UNPAY = 0;         // 订单状态为 0 未付款
    public static final int TYPE_UNSEND = 1;    // 订单状态为 1 未发货
    public static final int TYPE_WAITGET = 2;     // 订单状态为 2 已发货
    public static final int TYPE_FINISH = 3;        // 订单状态为 3 已完成
    private int id;
    private int medicineId;
    private int userId;

    private int count;
    private int price;
    private String medicineName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(int medicineId) {
        this.medicineId = medicineId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }


}
