package com.registration.hospital.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.registration.common.helper.helper.HttpRequestHelper;
import com.registration.hospital.entity.hospital.Department;
import com.registration.hospital.reposotory.DepartmentRepository;
import com.registration.hospital.service.DepartmentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;
    @Override
    public Boolean saveDepartment(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, Object> stringObjectMap = HttpRequestHelper.switchMap(parameterMap);


        String jsonString = JSONObject.toJSONString(stringObjectMap);
        Department department = JSONObject.parseObject(jsonString, Department.class);
        Department departmentByHosCodeAndDepcode = departmentRepository.getDepartmentByHoscodeAndDepcode(department.getHoscode(), department.getDepcode());
        if(departmentByHosCodeAndDepcode != null){
            departmentByHosCodeAndDepcode.setUpdateTime(new Date());
            departmentByHosCodeAndDepcode.setIsDeleted(0);
            departmentRepository.save(departmentByHosCodeAndDepcode);
        }else {
            department.setCreateTime(new Date());
            department.setUpdateTime(new Date());
            department.setIsDeleted(0);
            departmentRepository.save(department);
        }
        return true;
    }

    @Override
    public Page<Department> getDepartment(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, Object> stringObjectMap = HttpRequestHelper.switchMap(parameterMap);

        String jsonString = JSONObject.toJSONString(stringObjectMap);
        Department department = JSONObject.parseObject(jsonString, Department.class);
        //当前页 和 每页记录数
        int page = StringUtils.isBlank((CharSequence) stringObjectMap.get("page")) ? 1 : Integer.parseInt((String) stringObjectMap.get("page"));
        int limit = StringUtils.isBlank((CharSequence) stringObjectMap.get("limit")) ? 1 : Integer.parseInt((String) stringObjectMap.get("limit"));

        PageRequest pageRequest = PageRequest.of(page-1, limit);
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase(true);
        Example example = Example.of(department,matcher);

        return departmentRepository.findAll(example, pageRequest);
    }

    @Override
    public Boolean deleteDepartment(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, Object> stringObjectMap = HttpRequestHelper.switchMap(parameterMap);

        String jsonString = JSONObject.toJSONString(stringObjectMap);
        Department department = JSONObject.parseObject(jsonString, Department.class);

        Department departmentByHoscodeAndDepcode = departmentRepository.getDepartmentByHoscodeAndDepcode(department.getHoscode(), department.getDepcode());
        if(departmentByHoscodeAndDepcode != null){
            departmentRepository.deleteById(departmentByHoscodeAndDepcode.getId());
        }
        return true;
    }
}
