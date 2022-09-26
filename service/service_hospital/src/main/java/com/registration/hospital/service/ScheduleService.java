package com.registration.hospital.service;

import com.registration.hospital.entity.hospital.Schedule;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;

public interface ScheduleService {

    Boolean saveSchedule(HttpServletRequest request);

    Page<Schedule> getSchedule(HttpServletRequest request);

    Boolean deleteSchedule(HttpServletRequest request);
}
