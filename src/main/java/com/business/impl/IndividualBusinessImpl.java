package com.business.impl;

import com.business.IndividualBusiness;
import com.dto.IndividualDTO;
import com.entity.Individual;
import com.entity.UserAddress;
import com.exception.ErrorCode;
import com.exception.GeneralExceptionFactory;
import com.service.IIndividualService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IndividualBusinessImpl implements IndividualBusiness {
    @Autowired
    private IIndividualService iIndividualService;

    @Override
    public Individual updateIndividualInfo(Long userId, IndividualDTO individualDTO) {
        Individual individual = getIndividual(userId, individualDTO);
        boolean isSuccess = iIndividualService.updateById(individual);
        if (!isSuccess) {
            throw GeneralExceptionFactory.create(ErrorCode.DB_UPDATE_ERROR, "update individual info error");
        }
        return individual;
    }

    private Individual getIndividual(Long userId, IndividualDTO individualDTO) {
        Individual in = new Individual();
        in.setInsuranceCompany(individualDTO.getInsuranceCompany());
        in.setDriverLicence(individualDTO.getDriverLicence());
        in.setInsuranceNumber(individualDTO.getInsuranceNumber());
        in.setUserId(userId);
        return in;
    }

}
