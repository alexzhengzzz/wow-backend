package com.bo;

import lombok.Data;

@Data
public class ModelBO {
    public Long modelId;
    private Long manId;
    private String modelName;
    private String year;
    private Integer seatNum;
}
