package com.registration.hospital.service;

import com.registration.hospital.entity.hospital.Hospital;
import com.registration.hospital.vo.query.HospitalQueryVo;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;


public interface HospitalService {
    Boolean saveHospital(HttpServletRequest request);

    Hospital getHospital(HttpServletRequest request);

    Page<Hospital> getHospitalByPage(@PathVariable Integer page,
                                     @PathVariable Integer limit,
                                     HospitalQueryVo hospitalQueryVo);
}
