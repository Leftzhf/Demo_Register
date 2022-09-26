package com.registration.hospital.controller;

import com.registration.hospital.entity.hospital.Hospital;
import com.registration.hospital.service.HospitalService;
import com.registration.hospital.vo.query.HospitalQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "医院管理模块")
@RestController
@RequestMapping("/api/hospital")
public class HospitalController {
    @Autowired
    private HospitalService hospitalService;
    @PostMapping("/get/{page}/{limit}")
    @ApiOperation(value = "查询医院信息-分页")
    public Page<Hospital> getHospitalByPage(@PathVariable Integer page,
                                            @PathVariable Integer limit,
                                            HospitalQueryVo hospitalQueryVo){
        return hospitalService.getHospitalByPage(page, limit, hospitalQueryVo);
    }
}
