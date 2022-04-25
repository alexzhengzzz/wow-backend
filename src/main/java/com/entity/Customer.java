package com.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author zmh
 * @since 2022-04-12
 */
@TableName("yzm_customer")
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId
    private Integer custId;

    private String phoneNum;

    private String country;

    private String state;

    private String city;

    private String street;

    private String zipcode;

    private String email;

    private String custType;


    public Integer getCustId() {
        return custId;
    }

    public void setCustId(Integer custId) {
        this.custId = custId;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCustType() {
        return custType;
    }

    public void setCustType(String custType) {
        this.custType = custType;
    }

    @Override
    public String toString() {
        return "YzmCustomer{" +
        "custId=" + custId +
        ", phoneNum=" + phoneNum +
        ", country=" + country +
        ", state=" + state +
        ", city=" + city +
        ", street=" + street +
        ", zipcode=" + zipcode +
        ", email=" + email +
        ", custType=" + custType +
        "}";
    }
}
