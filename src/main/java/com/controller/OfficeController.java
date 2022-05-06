package com.controller;

import com.bo.OfficeSelectBO;
import com.bo.OfficeBO;
import com.business.OfficeBusiness;
import com.dto.OfficeDTO;
import com.enums.ResponseCode;
import com.utils.cache.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/office")
@Api("OfficeMaintain")
public class OfficeController {
    @Autowired
    OfficeBusiness officeBusiness;

    @ApiOperation("get availabe return office")
    @GetMapping("/nameList")
    public Response<List<OfficeSelectBO>> getAllOfficeList(){
        return new Response<>(ResponseCode.SUCCESS, officeBusiness.getOfficeList());
    }

    @ApiOperation("get availabe return office information")
    @GetMapping()
    public Response<List<OfficeBO>> getAllOfficeInfo(){
        return new Response<>(ResponseCode.SUCCESS, officeBusiness.getOfficeInfo());
    }

    @ApiOperation("create new Office")
    @PostMapping()
    public Response createOfficeInfo(@RequestBody OfficeDTO officeDTO){
        officeBusiness.createOfficeInfo(officeDTO);
        return new Response(ResponseCode.SUCCESS,"success");
    }

    @ApiOperation("delete office")
    @DeleteMapping ("/{officeId}")
    public Response deleteOffice(@PathVariable("officeId") Integer officeId){
        officeBusiness.deleteOfficeById(officeId);
        return new Response(ResponseCode.SUCCESS, "success");
    }

    @ApiOperation("update office")
    @PutMapping ("/{officeId}")
    public Response updateOffice(@PathVariable("officeId") Integer officeId, @RequestBody OfficeDTO officeDTO){
        officeBusiness.updateOffice(officeId, officeDTO);
        return new Response(ResponseCode.SUCCESS, "success");
    }

}
