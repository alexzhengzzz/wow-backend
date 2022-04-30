package com.business;

import com.dto.UserAddressDTO;
import com.entity.UserAddress;

public interface IUserAddressBusiness {

    UserAddress updateUserAddressById(Long userId, UserAddressDTO userAddressDTO);
}
