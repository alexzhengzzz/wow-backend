package com.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class RegisterCorporDTO {
    private String employeeId;
    private String companyName;
    private String registerCode;
}
