package com.registration.hospital.service;

import com.registration.hospital.entity.hospital.Department;
import com.registration.hospital.vo.service_hospital.DepartmentVO;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


public interface DepartmentService {
    Boolean saveDepartment(HttpServletRequest request);

    Page<Department> getDepartment(HttpServletRequest request);

    Boolean deleteDepartment(HttpServletRequest request);

    List<DepartmentVO> getDepartmentByHostCode(String hostCode);
}
