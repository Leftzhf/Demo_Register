package com.registration.hospital.controller;

import com.registration.hospital.service.DepartmentService;
import com.registration.service_hospital.vo.DepartmentVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Api(tags = "科室管理模块")
@RestController
@RequestMapping("/admin/hospital")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @ApiOperation("获取所有科室信息-通过医院编号")
    @GetMapping("/get/department/{hostCode}")
    public List<DepartmentVO> getDepartmentById(@PathVariable String hostCode){
        return departmentService.getDepartmentByHostCode(hostCode);
    }

}
