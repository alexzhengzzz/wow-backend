package com.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author zmh
 * @since 2022-04-26
 */
public class Individual implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value = "user_id")
    private Long userId;

    private String driverLicence;

    private String insuranceCompany;

    private String insuranceNumber;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getDriverLicence() {
        return driverLicence;
    }

    public void setDriverLicence(String driverLicence) {
        this.driverLicence = driverLicence;
    }

    public String getInsuranceCompany() {
        return insuranceCompany;
    }

    public void setInsuranceCompany(String insuranceCompany) {
        this.insuranceCompany = insuranceCompany;
    }

    public String getInsuranceNumber() {
        return insuranceNumber;
    }

    public void setInsuranceNumber(String insuranceNumber) {
        this.insuranceNumber = insuranceNumber;
    }

    @Override
    public String toString() {
        return "Individual{" +
        "userId=" + userId +
        ", driverLicence=" + driverLicence +
        ", insuranceCompany=" + insuranceCompany +
        ", insuranceNumber=" + insuranceNumber +
        "}";
    }
}
