package com.registration.hospital.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.registration.hospital.mapper")
public class HospitalConfig {
}
