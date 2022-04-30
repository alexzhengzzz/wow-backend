package com.business;


import com.dto.CorpEmployeeDTO;
import com.dto.CorporationDTO;
import com.entity.User;

import java.util.List;


public interface CorporationBusiness {
    void createCorporation(CorporationDTO corporationDTO);

    void deleteCorporation(CorporationDTO corporationDTO);

    void addEmployeeToCorporation(CorpEmployeeDTO corpEmployeeDTO);

    List<User> getEmployeeList(String companyName);
}
