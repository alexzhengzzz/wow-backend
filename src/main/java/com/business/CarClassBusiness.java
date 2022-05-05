package com.business;

import com.bo.CarClassSelectBO;
import com.bo.CarClassBO;

import java.util.List;

public interface CarClassBusiness {

    List<CarClassSelectBO> getClassList();

    List<CarClassBO> getClassInfo();
}
