package com.enums;

public enum PaymentCardStatus {

    VERIFIED("V"),
    DECLINED("D"),
    PENDING("P");

    private String status;
    PaymentCardStatus(String status){
        this.status = status;
    }

    public String getStatus(){
        return this.status;
    }

}
