package com.onepilltest.entity;

public class Cart {
    private String name;
    private String type;
    private int price;
    private String medicineSize;
    private int count;

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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
