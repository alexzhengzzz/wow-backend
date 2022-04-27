package com.business;


import com.dto.CorpEmployeeDTO;
import com.dto.CorporationDTO;
import com.utils.cache.Response;

public interface CorporationBusiness {
    void createCorporation(CorporationDTO corporationDTO);

    void addEmployeeToCorporation(CorpEmployeeDTO corpEmployeeDTO);
}
