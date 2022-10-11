package com.registration.hospital.controller;

import com.registration.hospital.service.HospitalService;
import com.registration.service_hospital.entity.hospital.Hospital;
import com.registration.service_hospital.vo.query.HospitalQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@Api(tags = "医院管理模块")
@RestController
@RequestMapping("/admin/hospital")
public class HospitalController {
    @Autowired
    private HospitalService hospitalService;

    @PostMapping("/get/{page}/{limit}")
    @ApiOperation(value = "查询医院信息-分页")
    public Page<Hospital> getHospitalByPage(@PathVariable Integer page,
                                            @PathVariable Integer limit,
                                            @RequestBody(required = false)
                                                HospitalQueryVo hospitalQueryVo){
        return hospitalService.getHospitalByPage(page, limit, hospitalQueryVo);
    }
    @ApiOperation(value = "更新医院状态—通过主键")
    @PostMapping("/update/hospital/{id}/status/{status}")
    public Boolean updateHospitalStatus(@PathVariable String id,
                                        @PathVariable Integer status){
        return hospitalService.updateHospitalStatus(id,status);
    }
    @ApiOperation(value = "查询医院信息-通过主键")
    @GetMapping("/get/{id}")
    public Hospital getHospitalInfo(@PathVariable String id){
        return hospitalService.getHospitalById(id);
    }

}
