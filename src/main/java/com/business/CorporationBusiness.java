package com.business;


import com.dto.CorpEmployeeDTO;
import com.dto.CorporationDTO;


public interface CorporationBusiness {
    void createCorporation(CorporationDTO corporationDTO);

    void deleteCorporation(CorporationDTO corporationDTO);

    void addEmployeeToCorporation(CorpEmployeeDTO corpEmployeeDTO);



}
