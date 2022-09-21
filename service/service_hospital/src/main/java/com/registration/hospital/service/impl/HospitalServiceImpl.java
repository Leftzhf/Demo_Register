package com.registration.hospital.service.impl;

import com.registration.hospital.reposotory.HospitalRepository;
import com.registration.hospital.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;

public class HospitalServiceImpl implements HospitalService {
    @Autowired
    private HospitalRepository hospitalRepository;
}
