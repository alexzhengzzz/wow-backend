package com.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class CarVO {
    private String vin_id;
    private String plate_number;
    private Long class_id;
    private String status;
    private Long office_id;
    private Long model_id;
}
