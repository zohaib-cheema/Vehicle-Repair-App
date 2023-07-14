package com.depauw.repairshop.database;

public class Repair {

    // Member
    private int rid;
    private int vehicleId;
    private String date;
    private double cost;
    private String description;

    public Repair(int rid, int vehicleId, String date, double cost, String description) {
        this.rid = rid;
        this.vehicleId = vehicleId;
        this.date = date;
        this.cost = cost;
        this.description = description;
    }

    public Repair(int vehicleId, String date, double cost, String description) {
        this.vehicleId = vehicleId;
        this.date = date;
        this.cost = cost;
        this.description = description;
    }

    public int getRid() {
        return rid;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public String getDate() {
        return date;
    }

    public double getCost() {
        return cost;
    }

    public String getDescription() {
        return description;
    }
}
