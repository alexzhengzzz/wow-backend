package com.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author zmh
 * @since 2022-05-02
 */
@TableName("yzm_office")
public class Office implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "office_id", type = IdType.AUTO)
    private Integer officeId;

    private String name;

    private String country;

    private String state;

    private String city;

    private String street;

    private String zipcode;

    private String phoneNum;


    public Integer getOfficeId() {
        return officeId;
    }

    public void setOfficeId(Integer officeId) {
        this.officeId = officeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    @Override
    public String toString() {
        return "YzmOffice{" +
        "officeId=" + officeId +
        ", name=" + name +
        ", country=" + country +
        ", state=" + state +
        ", city=" + city +
        ", street=" + street +
        ", zipcode=" + zipcode +
        ", phoneNum=" + phoneNum +
        "}";
    }
}
