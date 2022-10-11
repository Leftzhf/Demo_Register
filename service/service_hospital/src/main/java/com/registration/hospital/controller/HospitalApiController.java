package com.registration.hospital.controller;

import com.registration.hospital.service.DepartmentService;
import com.registration.hospital.service.HospitalService;
import com.registration.service_hospital.entity.hospital.Hospital;
import com.registration.service_hospital.vo.DepartmentVO;
import com.registration.service_hospital.vo.query.HospitalQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "前台首页接口")
@RequestMapping("/api/hospital/")
public class HospitalApiController {

    @Autowired
    private HospitalService hospitalService;

    @Autowired
    private DepartmentService departmentService;

    @PostMapping("/get/{page}/{limit}")
    @ApiOperation(value = "查询医院信息-分页")
    public Page<Hospital> getHospitalByPage(@PathVariable Integer page,
                                            @PathVariable Integer limit,
                                            HospitalQueryVo hospitalQueryVo){
        return hospitalService.getHospitalByPage(page, limit, hospitalQueryVo);
    }

    @GetMapping("/likes")
    @ApiOperation(value = "模糊查询医院信息-根据医院名称")
    public List<Hospital> getHospitalByName (HospitalQueryVo hospitalQueryVo){
        return hospitalService.getHospitalByName(hospitalQueryVo.getHosname());
    }

    @GetMapping("getInfo/{hoscode}")
    @ApiOperation(value = "查询医院信息-根据医院编码")
    public Hospital getHospitalInfo(@PathVariable("hoscode") String hosCode){
        return hospitalService.getHospitalInfo(hosCode);
    }

    @ApiOperation("获取所有科室信息-通过医院编号")
    @GetMapping("/get/department/{hostCode}")
    public List<DepartmentVO> getDepartmentById(@PathVariable String hostCode){
        return departmentService.getDepartmentByHostCode(hostCode);
    }
}
