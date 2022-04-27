package com.business;

import com.dto.UserAddressDTO;

public interface IUserAddressBusiness {

    void updateUserAddressById(Long userId, UserAddressDTO userAddressDTO);
}
