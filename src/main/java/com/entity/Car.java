package com.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Deprecated
@Data
@TableName("yzm_vehicles")
public class Car {
    @TableId(value = "vin_id", type = IdType.AUTO)
    private String vin_id;
    private String plate_number;
    private Long class_id;
    private String status;
    private Long office_id;
    private Long model_id;
}
