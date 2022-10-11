package com.registration.hospital.controller;

import com.registration.service_hospital.entity.hospital.Department;
import com.registration.service_hospital.entity.hospital.Hospital;
import com.registration.service_hospital.entity.hospital.Schedule;
import com.registration.hospital.service.DepartmentService;
import com.registration.hospital.service.HospitalService;
import com.registration.hospital.service.ScheduleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/hosp")
@Api(tags = "医院管理系统接口")
public class HospitalManagerController {



    @Autowired
    private HospitalService hospitalService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private ScheduleService scheduleService;

    @ApiOperation(value = "医院管理系统-上传医院信息")
    @PostMapping("/saveHospital")
    public boolean saveHospital(HttpServletRequest request){
        return hospitalService.saveHospital(request);
    }
    @ApiOperation(value = "医院管理系统-查询医院信息")
    @PostMapping("/hospital/show")
    public Hospital getHospital(HttpServletRequest request){
        return hospitalService.getHospital(request);
    }
    @ApiOperation(value = "医院管理系统-上传科室信息")
    @PostMapping("saveDepartment")
    public Boolean saveDepartment(HttpServletRequest request){
        return departmentService.saveDepartment(request);
    }
    @ApiOperation(value = "医院管理系统-查询科室信息-分页")
    @PostMapping("department/list")
    public Page<Department> getDepartment (HttpServletRequest request){
        return departmentService.getDepartment(request);
    }
    @ApiOperation(value = "医院管理系统-删除科室信息")
    @PostMapping("department/remove")
    public Boolean deleteDepartment(HttpServletRequest request){
        return departmentService.deleteDepartment(request);
    }
    @ApiOperation(value = "医院管理系统-上传排班表")
    @PostMapping("saveSchedule")
    public Boolean saveSchedule(HttpServletRequest request){
        return scheduleService.saveSchedule(request);
    }
    @ApiOperation(value = "医院管理系统-查询排班信息-分页")
    @PostMapping("schedule/list")
    public Page<Schedule> getSchedule(HttpServletRequest request) {
        return scheduleService.getSchedule(request);
    }
    @ApiOperation(value = "医院管理系统-删除排班信息")
    @PostMapping("schedule/remove")
    public Boolean deleteSchedule(HttpServletRequest request){
        return scheduleService.deleteSchedule(request);
    }
}
