package com.business.impl;

import com.bo.CarInfoBO;
import com.business.CarInfoBusiness;
import com.business.util.CarInfoBOUtil;
import com.dto.*;
import com.entity.*;
import com.enums.VehicleStatus;
import com.service.*;
import com.service.util.CarClassUtil;
import com.service.util.ModelUtil;
import com.service.util.OfficeUtil;
import com.service.util.VehicleUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class CarInfoBusinessImpl implements CarInfoBusiness {
    @Autowired
    private ICarInfoService carInfoService;

    @Autowired
    private IOfficeService officeService;

    @Autowired
    private IManufactureService manufactureService;

    @Autowired
    private ICarClassService carClassService;

    @Autowired
    private IModelService modelService;

    @Autowired
    private IVehicleService vehicleService;

    @Override
    public List<CarInfoBO> getCarList(int type){
        return CarInfoBOUtil.transferList(carInfoService.getCarList(), type);
    }

    private Integer manId;
    private Integer carClassId;
    private Integer modelId;
    private Integer officeId;
    private String vinId;
    private String plateNumber;
    private String manName;

    private CarClassDTO carClassDTO;
    private ModelDTO modelDTO;
    private OfficeDTO officeDTO;

    private Vehicle vehicle;

    @Override
    public boolean updateCar(CarInfoDTO carInfoDTO, boolean isExist) {
        setCarInfo(carInfoDTO);
        setProperties();
        createVehicle();
        if(isExist){
            vehicleService.updateById(vehicle);
        }else{
            vehicleService.save(vehicle);
        }
        return true;
    }

    @Override
    public boolean deleteCar(String vinId) {
        return vehicleService.removeById(vinId);
    }

    public void setCarInfo(CarInfoDTO carInfoDTO){
        this.manId = carInfoDTO.getManId();
        this.carClassId = carInfoDTO.getCarClassId();
        this.modelId = carInfoDTO.getModelId();
        this.officeId = carInfoDTO.getOfficeId();
        this.vinId = carInfoDTO.getVehicleId();
        this.plateNumber = carInfoDTO.getPlateNumber();
        this.manName = carInfoDTO.getManName();

        this.carClassDTO = carInfoDTO.getCarClassDTO();
        this.modelDTO = carInfoDTO.getModelDTO();
        this.officeDTO = carInfoDTO.getOfficeDTO();
    }

    public void setProperties(){
        if(manId == null){
            Manufacture manufacture = new Manufacture();
            manufacture.setManName(manName);
            manufactureService.insert(manufacture);
            manId = manufacture.getManId();
        }
        if(modelDTO!=null){
            modelDTO.setManId(manId);
        }
        if(carClassId == null){
            CarClass carClass = CarClassUtil.transferDTO( null,carClassDTO );
            carClassService.insert(carClass);
            carClassId = carClass.getClassId();
        }

        if(modelId == null){
            Model model = ModelUtil.transferDTO(null, modelDTO);
            modelService.insert(model);
            modelId = model.getModelId();
        }
        if(officeId == null){
            Office office = OfficeUtil.transferDTO(officeDTO);
            officeService.insert(office);
            officeId = office.getOfficeId();
        }
    }

    private void createVehicle(){
        VehicleDTO vehicleDTO = new VehicleDTO();

        vehicleDTO.setClassId(carClassId);
        vehicleDTO.setModelId(modelId);
        vehicleDTO.setOfficeId(officeId);
        vehicleDTO.setVinId(vinId);
        vehicleDTO.setStatus(VehicleStatus.IN_STOCK.getStatus());
        vehicleDTO.setPlateNumber(plateNumber);

        vehicle = VehicleUtil.transferDTO(vinId,vehicleDTO);
    }


}
