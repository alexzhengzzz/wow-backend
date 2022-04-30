package com.controller;

import com.annotation.PermissionChecker;
import com.business.impl.UserAddressBusinessImpl;
import com.dto.UserAddressDTO;
import com.entity.UserAddress;
import com.enums.ResponseCode;
import com.enums.Role;
import com.utils.cache.Response;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/useraddress")
public class UserAddressController {
    @Autowired
    private UserAddressBusinessImpl userAddressBusiness;

    @ApiOperation("update useraddress")
    @PutMapping("/{userId}")
    @PermissionChecker(requiredRole = Role.USER)
    public Response<UserAddress> updateUserAddressById(@PathVariable("userId") Long userId, @RequestBody UserAddressDTO userAddressDTO) {
        UserAddress newUserAddress = userAddressBusiness.updateUserAddressById(userId, userAddressDTO);
        return new Response<>(ResponseCode.SUCCESS, newUserAddress);
    }
}
