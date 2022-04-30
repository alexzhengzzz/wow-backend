package com.controller;

import com.annotation.PermissionChecker;
import com.business.IndividualBusiness;
import com.dto.IndividualDTO;
import com.entity.Individual;
import com.enums.ResponseCode;
import com.enums.Role;
import com.utils.cache.Response;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/individual")
@Slf4j
public class IndividualController {
    @Autowired
    private IndividualBusiness individualBusiness;

    @ApiOperation("update Individual information")
    @PutMapping("/{userId}")
    @PermissionChecker(requiredRole = Role.USER)
    public Response<Individual> updateIndividualInfo(@PathVariable("userId") Long userId, @RequestBody IndividualDTO individualDTO) {
        Individual individual = individualBusiness.updateIndividualInfo(userId, individualDTO);
        return new Response<>(ResponseCode.SUCCESS, individual);
    }
}
