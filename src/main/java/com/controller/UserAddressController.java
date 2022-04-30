package com.controller;

import com.annotation.PermissionChecker;
import com.business.impl.UserAddressBusinessImpl;
import com.dto.UserAddressDTO;
import com.entity.UserAddress;
import com.enums.ResponseCode;
import com.enums.Role;
import com.service.IUserAddressService;
import com.utils.cache.Response;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zmh
 * @since 2022-04-26
 */
@RestController
@RequestMapping("/api/useraddress")
public class UserAddressController {
    // update user address
    @Autowired
    private UserAddressBusinessImpl userAddressBusiness;

    @ApiOperation("update user address")
    @PutMapping("/{userId}")
    @PermissionChecker(requiredRole = Role.USER)
    public Response<ResponseCode> updateUserAddressById(@PathVariable("userId") Long userId, @RequestBody UserAddressDTO userAddressDTO) {
        // update user address by email
        userAddressBusiness.updateUserAddressById(userId, userAddressDTO);
        // return success
        return new Response<>(ResponseCode.SUCCESS);
    }
}
