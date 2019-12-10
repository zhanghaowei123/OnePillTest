package com.onepilltest.entity;

import java.util.ArrayList;
import java.util.List;

public class Medicine {
    private List<Integer> ids = new ArrayList<>();
    //通用名
    private List<String> generaNames=new ArrayList<>();
    //学名
    private List<String> medicineNames=new ArrayList<>();
    //价格
    private List<Integer> prices = new ArrayList<>();
    //概述.
    private List<String> overViews = new ArrayList<>();
    //功能主治
    private List<String> functions = new ArrayList<>();
    //使用说明
    private List<String> introdutions= new ArrayList<>();
    //副作用
    private List<String> side_effects = new ArrayList<>();
    //禁忌
    private List<String> forbiddancets = new ArrayList<>();
    //咨询医生
    private int doctorID;
    //规格
    private List<String> standards= new ArrayList<>();
    //种类（属于哪一方面的药）
    private List<String> kinds = new ArrayList<>();
    //库存
    private List<Integer> stocks = new ArrayList<>();
    public int getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(int doctorID) {
        this.doctorID = doctorID;
    }
    public List<Integer> getId() {
        return ids;
    }

    public void setId(int id) {
        ids.add(id);
    }

    public List<String> getGeneraName() {
        return generaNames;
    }

    public void setGeneraName(String generaName) {
        generaNames.add(generaName);
    }

    public List<String> getMedicineName() {
        return medicineNames;
    }

    public void setMedicineName(String medicineName) {
        medicineNames.add(medicineName);
    }

    public List<Integer> getPrice() {
        return prices;
    }

    public void setPrice(int price) {
        prices.add(price);
    }

    public List<String> getOverView() {
        return overViews;
    }

    public void setOverView(String overView) {
        overViews.add(overView);
    }

    public List<String> getFunction() {
        return functions;
    }

    public void setFunction(String function) {
        functions.add(function);
    }

    public List<String> getIntrodution() {
        return introdutions;
    }

    public void setIntrodution(String introdution) {
        introdutions.add(introdution);
    }

    public List<String> getSide_effect() {
        return side_effects;
    }

    public void setSide_effect(String side_effect) {
        side_effects.add(side_effect);
    }

    public List<String> getForbiddancet() {
        return forbiddancets;
    }

    public void setForbiddancet(String forbiddancet) {
        forbiddancets.add(forbiddancet);
    }

    public List<String> getStandard() {
        return standards;
    }

    public void setStandard(String standard) {
        standards.add(standard);
    }

    public List<String> getKinds() {
        return kinds;
    }

    public void setKinds(String kind) {
        kinds.add(kind);
    }
    public List<Integer> getStocks() {
        return stocks;
    }

    public void setStocks(int stock) {
        stocks.add(stock);
    }
    @Override
    public String toString() {
        return "Medicine:"+"id"+
                ids+"generaName:"+generaNames;
    }

}
