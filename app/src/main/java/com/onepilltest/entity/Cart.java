package com.onepilltest.entity;

import java.io.Serializable;

public class Cart {


    private int id;
    private String name;
    private String type;
    private int medicinePrice;
    private String medicineSize;
    private int count;
    private Medicine medicine;

    public Serializable getMedicine() {
        return (Serializable) medicine;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMedicineSize() {
        return medicineSize;
    }

    public void setMedicineSize(String medicineSize) {
        this.medicineSize = medicineSize; }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getMedicinePrice() {
        return medicinePrice;
    }

    public void setMedicinePrice(int price) {
        this.medicinePrice = price;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
