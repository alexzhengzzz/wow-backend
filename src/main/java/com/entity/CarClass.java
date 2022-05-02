package com.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 
 * </p>
 *
 * @author zmh
 * @since 2022-05-02
 */
@TableName("yzm_car_class")
public class CarClass implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "class_id", type = IdType.AUTO)
    private Integer classId;

    private String classType;

    private String imageUrl;

    private String rentalRatePerDay;

    private BigDecimal overFee;


    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getRentalRatePerDay() {
        return rentalRatePerDay;
    }

    public void setRentalRatePerDay(String rentalRatePerDay) {
        this.rentalRatePerDay = rentalRatePerDay;
    }

    public BigDecimal getOverFee() {
        return overFee;
    }

    public void setOverFee(BigDecimal overFee) {
        this.overFee = overFee;
    }

    @Override
    public String toString() {
        return "YzmCarClass{" +
        "classId=" + classId +
        ", classType=" + classType +
        ", imageUrl=" + imageUrl +
        ", rentalRatePerDay=" + rentalRatePerDay +
        ", overFee=" + overFee +
        "}";
    }
}
