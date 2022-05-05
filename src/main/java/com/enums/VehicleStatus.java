package com.enums;

public enum VehicleStatus {
    IN_STOCK("I"),
    OUT_STOCK("O");

    String status;

    VehicleStatus(String status){
        this.status = status;
    }

    public String getStatus(){
        return this.status;
    }

}
