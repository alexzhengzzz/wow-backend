package com.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("yzm_manufacture")
public class Manufacture implements Serializable {
    @TableId(value = "man_id", type = IdType.AUTO)
    private Integer manId;
    private String manName;
}
