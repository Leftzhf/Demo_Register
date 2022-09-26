package com.registration.hospital.service;

import com.registration.hospital.entity.hospital.Department;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;


public interface DepartmentService {
    Boolean saveDepartment(HttpServletRequest request);

    Page<Department> getDepartment(HttpServletRequest request);

    Boolean deleteDepartment(HttpServletRequest request);
}
