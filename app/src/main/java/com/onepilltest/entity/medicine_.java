package com.onepilltest.entity;

public class medicine_ {
    private int id;
    private String generalName;
    private String medicine;
    private String price;
    private String overview;
    private String function;
    private String introdutions;
    private String side_effect;
    private String forbiddance;
    private int doctor_id;
    private String img1;
    private String img2;
    private String img3;
    private String standard;

    public medicine_() {
    }

    public medicine_(int id, String generalName, String medicine, String price, String overview, String function, String introdutions, String side_effect, String forbiddance, int doctor_id, String img1, String img2 , String img3, String standard) {
        this.id = id;
        this.generalName = generalName;
        this.medicine = medicine;
        this.price = price;
        this.overview = overview;
        this.function = function;
        this.introdutions = introdutions;
        this.side_effect = side_effect;
        this.forbiddance = forbiddance;
        this.doctor_id = doctor_id;
        this.img1 = img1;
        this.img2 = img2;
        this.img3 = img3;
        this.standard = standard;
    }

    public String getImg2() {
        return img2;
    }

    public void setImg2(String img2) {
        this.img2 = img2;
    }

    public String getImg3() {
        return img3;
    }

    public void setImg3(String img3) {
        this.img3 = img3;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGeneralName() {
        return generalName;
    }

    public void setGeneralName(String generalName) {
        this.generalName = generalName;
    }

    public String getMedicine() {
        return medicine;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getIntrodutions() {
        return introdutions;
    }

    public void setIntrodutions(String introdutions) {
        this.introdutions = introdutions;
    }

    public String getSide_effect() {
        return side_effect;
    }

    public void setSide_effect(String side_effect) {
        this.side_effect = side_effect;
    }

    public String getForbiddance() {
        return forbiddance;
    }

    public void setForbiddance(String forbiddance) {
        this.forbiddance = forbiddance;
    }

    public int getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(int doctor_id) {
        this.doctor_id = doctor_id;
    }

    public String getImg1() {
        return img1;
    }

    public void setImg1(String img1) {
        this.img1 = img1;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }
}
