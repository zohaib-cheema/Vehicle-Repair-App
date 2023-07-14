package com.depauw.repairshop.database;

public class Vehicle {

    // Member
    private int vid;
    private int year;
    private String makeAndModel;
    private double purchasePrice;
    private int isNew;

    public Vehicle(int vid, int year, String makeAndModel, double purchasePrice, int isNew) {
        this.vid = vid;
        this.year = year;
        this.makeAndModel = makeAndModel;
        this.purchasePrice = purchasePrice;
        this.isNew = isNew;
    }

    public Vehicle(int year, String makeAndModel, double purchasePrice, int isNew) {
        this.year = year;
        this.makeAndModel = makeAndModel;
        this.purchasePrice = purchasePrice;
        this.isNew = isNew;
    }

    public int getVid() { return vid; }

    public int getYear() {
        return year;
    }

    public String getMakeAndModel() {
        return makeAndModel;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public int isNew() {
        return isNew;
    }

    @Override
    public String toString(){
        return "" + year + " " + makeAndModel;
    }
}
