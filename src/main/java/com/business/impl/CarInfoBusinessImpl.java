package com.business.impl;

import com.bo.CarInfoBO;
import com.business.CarInfoBusiness;
import com.business.util.CarInfoBOUtil;
import com.dto.*;
import com.entity.CarClass;
import com.entity.Manufacture;
import com.entity.Model;
import com.entity.Office;
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

    @Override
    public boolean createCar(CarInfoDTO carInfoDTO) {

        Integer manId = carInfoDTO.getManId();
        Integer carClassId = carInfoDTO.getCarClassId();
        Integer modelId = carInfoDTO.getModelId();
        Integer officeId = carInfoDTO.getOfficeId();
        String vinId = carInfoDTO.getVehicleId();
        String plateNumber = carInfoDTO.getPlateNumber();
        String manName = carInfoDTO.getManName();

        CarClassDTO carClassDTO = carInfoDTO.getCarClassDTO();
        ModelDTO modelDTO = carInfoDTO.getModelDTO();
        OfficeDTO officeDTO = carInfoDTO.getOfficeDTO();

        if(manId == null){
            Manufacture manufacture = new Manufacture();
            manufacture.setManName(manName);
            manufactureService.insert(manufacture);
            manId = manufacture.getManId();
        }
        modelDTO.setManId(manId);

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

        VehicleDTO vehicleDTO = new VehicleDTO();

        vehicleDTO.setClassId(carClassId);
        vehicleDTO.setModelId(modelId);
        vehicleDTO.setOfficeId(officeId);
        vehicleDTO.setVinId(vinId);
        vehicleDTO.setStatus(VehicleStatus.IN_STOCK.getStatus());
        vehicleDTO.setPlateNumber(plateNumber);

        vehicleService.save(VehicleUtil.transferDTO(vinId,vehicleDTO));

        return false;
    }
}
