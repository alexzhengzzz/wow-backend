package com.vo;

import lombok.Data;

@Data
public class ModelVO {
    public Integer modelId;
    private Integer manId;
    private String modelName;
    private String year;
    private Integer seatNum;
}
