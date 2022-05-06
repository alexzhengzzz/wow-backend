package com.bo;

import lombok.Data;

@Data
public class ModelBO {
    public Integer modelId;
    private Integer manId;
    private String modelName;
    private String year;
    private Integer seatNum;
}
