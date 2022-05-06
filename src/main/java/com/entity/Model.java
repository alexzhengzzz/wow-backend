package com.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("yzm_model")
public class Model implements Serializable {
    @TableId(value = "model_id", type = IdType.AUTO)
    public Integer modelId;
    private Long manId;
    private String modelName;
    private String year;
    private Integer seatNum;
}
