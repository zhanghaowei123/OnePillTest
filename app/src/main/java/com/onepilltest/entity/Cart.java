package com.onepilltest.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Cart implements Serializable {


    private int id;
    private String name;
    //药品规格
    private String type;
    private int medicineId;
    private int medicinePrice;
    private String medicineSize;
    private int count;
    private Medicine medicine;
    private medicine_ medicines;
    private int buyerId;
    public static List<Integer> medicineList = new ArrayList<>();

    public int getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(int buyerId) {
        this.buyerId = buyerId;
    }

    public medicine_ getMedicines() {
        return medicines;
    }

    public void setMedicines(medicine_ medicines) {
        this.medicines = medicines;
    }

    public Medicine getMedicine() {
        return medicine;
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
        this.medicineSize = medicineSize;
    }

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

    public int getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(int medicineId) {
        this.medicineId = medicineId;
    }


    @Override
    public String toString() {
        return super.toString();
    }
}
