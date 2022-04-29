package com.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author zmh
 * @since 2022-04-26
 */
@TableName("corp_employee")
public class CorpEmployee implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId("corp_id")
    private Long corpId;
    private String employeeId;


    public Long getCorpId() {
        return corpId;
    }

    public void setCorpId(Long corpId) {
        this.corpId = corpId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    @Override
    public String toString() {
        return "CorpEmployee{" +
        "corpId=" + corpId +
        ", employeeId=" + employeeId +
        "}";
    }
}
