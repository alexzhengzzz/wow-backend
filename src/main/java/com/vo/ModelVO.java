package com.vo;

import lombok.Data;

@Data
public class ModelVO {
    public Long modelId;
    private Long manId;
    private String modelName;
    private String year;
    private Integer seatNum;
}
