package com.business;

public interface CarMaintainBusiness{

    // 更新车信息
    boolean updateOffice();

    boolean updateVehicle();

    boolean updateCarClass();

    boolean updateManufacture();

    //新建车信息
    boolean createOffice();

    boolean createVehicle();

    boolean createCarClass();

    boolean createManufacture();

    //删除车信息
    boolean deleteVehicle();

    boolean deleteOffice();

    boolean deleteCarClass();

    boolean deleteManufacture();


}
