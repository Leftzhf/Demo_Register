package com.registration.hospital.reposotory;

import com.registration.service_hospital.entity.hospital.Department;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends MongoRepository<Department,String> {

    Department getDepartmentByHoscodeAndDepcode(String hoscode, String depcode);
    List<Department> getDepartmentByHoscode(String hoscode);

}
