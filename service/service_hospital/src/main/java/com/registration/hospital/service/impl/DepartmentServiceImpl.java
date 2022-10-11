package com.registration.hospital.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.registration.common.helper.helper.HttpRequestHelper;
import com.registration.service_hospital.entity.hospital.Department;
import com.registration.hospital.reposotory.DepartmentRepository;
import com.registration.hospital.service.DepartmentService;
import com.registration.service_hospital.vo.DepartmentVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        int page = StringUtils.isBlank((String) stringObjectMap.get("page")) ? 1 : Integer.parseInt((String) stringObjectMap.get("page"));
        int limit = StringUtils.isBlank((String) stringObjectMap.get("limit")) ? 1 : Integer.parseInt((String) stringObjectMap.get("limit"));

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

    @Override
    public List<DepartmentVO> getDepartmentByHostCode(String hostCode) {
        List<Department> departmentByHoscode = departmentRepository.getDepartmentByHoscode(hostCode);
        Map<String, List<Department>> maps = departmentByHoscode.stream().collect(Collectors.groupingBy(Department::getBigcode));
        List<DepartmentVO> departmentList = new ArrayList<>();
        maps.entrySet().forEach(map->{
            DepartmentVO departmentVO  = new DepartmentVO();
            departmentVO.setParementDepartmentCode(map.getKey());
            departmentVO.setDepname(map.getValue().get(0).getBigname());
            departmentVO.setDepartmentList(map.getValue());
            departmentList.add(departmentVO);
        });
        return departmentList;
    }
}
