package com.business.impl;

import com.annotation.PermissionChecker;
import com.business.IUserAddressBusiness;
import com.dto.UserAddressDTO;
import com.entity.UserAddress;
import com.enums.Role;
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
    @PermissionChecker(requiredRole = Role.USER)
    public UserAddress updateUserAddressById(Long userId, UserAddressDTO userAddressDTO) {
        UserAddress us = userAddressService.getById(userId);
        if (us == null) {
            throw GeneralExceptionFactory.create(ErrorCode.DB_QUERY_NOT_EXISTED_ERROR, "User address not existed");
        }
        UserAddress newUserAddress = getUserAddress(userId, userAddressDTO);
        userAddressService.updateById(newUserAddress);
        return newUserAddress;
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
