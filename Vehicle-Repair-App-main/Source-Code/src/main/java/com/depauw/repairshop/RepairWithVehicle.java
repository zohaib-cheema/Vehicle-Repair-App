package com.depauw.repairshop;

import com.depauw.repairshop.database.Repair;
import com.depauw.repairshop.database.Vehicle;

public class RepairWithVehicle {

    private Vehicle vehicle;
    private Repair repair;

    public RepairWithVehicle(Vehicle vehicle, Repair repair) {
        this.vehicle = vehicle;
        this.repair = repair;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public Repair getRepair() {
        return repair;
    }
}
