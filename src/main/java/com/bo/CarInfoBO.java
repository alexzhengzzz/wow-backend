package com.bo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CarInfoBO {
    private String vin_id;
    private String image_url;
    private String class_type;
    private String status;
    private String name;
    private String year;
    // image 如何处理
    private Long seat;
    private final String powerMode = "Gasoline";
    private final Double mpg = 1.0;
    private final String driveMode = "Manual";
    private BigDecimal pricePerDay;
    private BigDecimal overFee;
    private BigDecimal limitMilePerDay;


    private Integer manId;
    private Integer officeId;
    private String officeName;
    private String manName;
}
