package com.controller;

import com.business.CorporationBusiness;
import com.dto.CorpEmployeeDTO;
import com.dto.CorporationDTO;
import com.dto.RegisterDTO;
import com.service.ICorporationService;
import com.service.impl.CorporationServiceImpl;
import com.utils.cache.Response;
import com.vo.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/corporation")
@Api("Corporation")
public class CorporationController {
    private final Logger logger = LoggerFactory.getLogger(CorporationController.class);

    @Autowired
    private CorporationBusiness corporationBusiness;
    @ApiOperation("add new corporation")
    @PostMapping
    public Response createCorporation(@RequestBody CorporationDTO corporationDTO) {
        corporationBusiness.createCorporation(corporationDTO);
        return new Response<>(200);
    }

    @ApiOperation("add new employee to existed corporation")
    @PostMapping("/employee")
    public Response addEmployeeToCorporation(@RequestBody CorpEmployeeDTO corpEmployeeDTO) {
        corporationBusiness.addEmployeeToCorporation(corpEmployeeDTO);
        return new Response<>(200);
    }



}
