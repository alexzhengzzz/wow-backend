package com.business.impl;

import com.business.IUserAddressBusiness;
import com.dto.UserAddressDTO;
import com.entity.UserAddress;
import com.exception.ErrorCode;
import com.exception.GeneralExceptionFactory;
import com.service.IUserAddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserAddressBusinessImpl implements IUserAddressBusiness {
    @Autowired
    private IUserAddressService userAddressService;

    @Override
    public void updateUserAddressById(Long userId, UserAddressDTO userAddressDTO) {
        // check if existed user address
        UserAddress us = userAddressService.getById(userId);
        if (us == null) {
            throw GeneralExceptionFactory.create(ErrorCode.UNKNOWN_ERROR, "User address not found");
        }
        // get user address
        UserAddress userAddress = getUserAddress(userId, userAddressDTO);
        userAddressService.updateById(userAddress);
    }

    private UserAddress getUserAddress(Long userId, UserAddressDTO userAddressDTO) {
        UserAddress userAddress = new UserAddress();
        userAddress.setUserId(userId);
        userAddress.setCity(userAddressDTO.getCity());
        userAddress.setCountry(userAddressDTO.getCountry());
        userAddress.setState(userAddressDTO.getState());
        userAddress.setZipcode(userAddressDTO.getZipcode());
        userAddress.setStreet(userAddressDTO.getStreet());
        return userAddress;
    }
}