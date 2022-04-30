package com.business;

import com.dto.IndividualDTO;
import com.entity.Individual;

public interface IndividualBusiness {
    Individual updateIndividualInfo(Long userId, IndividualDTO individualDTO);
}
