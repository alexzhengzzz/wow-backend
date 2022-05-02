package com.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author zmh
 * @since 2022-05-02
 */
@TableName("yzm_vehicles")
public class Vehicle implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value = "vin_id", type = IdType.AUTO)
    private String vinId;

    private String plateNumber;

    private Integer classId;

    private String status;

    private Integer officeId;

    private Integer modelId;


    public String getVinId() {
        return vinId;
    }

    public void setVinId(String vinId) {
        this.vinId = vinId;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getOfficeId() {
        return officeId;
    }

    public void setOfficeId(Integer officeId) {
        this.officeId = officeId;
    }

    public Integer getModelId() {
        return modelId;
    }

    public void setModelId(Integer modelId) {
        this.modelId = modelId;
    }

    @Override
    public String toString() {
        return "YzmVehicles{" +
                "vinId=" + vinId +
                ", plateNumber=" + plateNumber +
                ", classId=" + classId +
                ", status=" + status +
                ", officeId=" + officeId +
                ", modelId=" + modelId +
                "}";
    }
}
