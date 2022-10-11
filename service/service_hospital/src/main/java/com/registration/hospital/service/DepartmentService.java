package com.registration.hospital.service;

import com.registration.service_hospital.entity.hospital.Department;
import com.registration.service_hospital.vo.DepartmentVO;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


public interface DepartmentService {
    Boolean saveDepartment(HttpServletRequest request);

    Page<Department> getDepartment(HttpServletRequest request);

    Boolean deleteDepartment(HttpServletRequest request);

    List<DepartmentVO> getDepartmentByHostCode(String hostCode);
}
