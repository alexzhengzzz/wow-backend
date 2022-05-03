package com.controller;

import com.business.CorporationBusiness;
import com.dto.CorpEmployeeDTO;
import com.dto.CorporationDTO;
import com.enums.ResponseCode;
import com.utils.cache.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/corporation")
@Api("Corporation")
public class CorporationController {
    @Autowired
    private CorporationBusiness corporationBusiness;

    @ApiOperation("add new corporation, admin only!!!")
    @PostMapping
    public Response<ResponseCode> createCorporation(@RequestBody CorporationDTO corporationDTO) {
        corporationBusiness.createCorporation(corporationDTO);
        return new Response<>(ResponseCode.SUCCESS);
    }

    @ApiOperation("add new employee to existed corporation")
    @PostMapping("/employee")
    public Response<ResponseCode> addEmployeeToCorporation(@RequestBody CorpEmployeeDTO corpEmployeeDTO) {
        corporationBusiness.addEmployeeToCorporation(corpEmployeeDTO);
        return new Response<>(ResponseCode.SUCCESS);
    }

    @ApiOperation("delete corporation, admin only")
    @DeleteMapping
    public Response<ResponseCode> deleteCorporation(@RequestBody CorporationDTO corporationDTO) {
        corporationBusiness.deleteCorporation(corporationDTO);
        return new Response<>(ResponseCode.SUCCESS);
    }


}
