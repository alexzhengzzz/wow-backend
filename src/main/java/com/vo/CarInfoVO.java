package com.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CarInfoVO {
    private String vin_id;
    private String status;
    private String image_url;
    private String name;
    private String year;
    private String class_type;
    // image 如何处理
    private Long seat;
   // private final String powerMode = "Gasoline";
   // private final Double mpg = 1.0;
   // private final String driveMode = "Manual";
    private BigDecimal pricePerDay;
    private BigDecimal overFee;
    private BigDecimal limitMilePerDay;


    private Integer manId;
    private Integer officeId;
    private String officeName;
    private String manName;
}