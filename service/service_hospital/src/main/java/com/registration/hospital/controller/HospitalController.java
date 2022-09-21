package com.registration.hospital.controller;

import com.registration.hospital.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hosp")
public class HospitalController {
    @Autowired
    private HospitalService hospitalService;
}
