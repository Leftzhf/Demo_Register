package com.registration.hospital.service;

import com.registration.hospital.entity.hospital.Schedule;
import com.registration.hospital.vo.service_hospital.BookingScheduleRuleVO;
import com.registration.hospital.vo.service_hospital.ScheduleVO;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;

public interface ScheduleService {

    Boolean saveSchedule(HttpServletRequest request);

    Page<Schedule> getSchedule(HttpServletRequest request);

    Boolean deleteSchedule(HttpServletRequest request);

    Schedule getScheduleById (String id);

    BookingScheduleRuleVO getBookingScheduleList(Long page,
                                               Long limit,
                                               String hosCode,
                                               String depCode);

    ScheduleVO getScheduleList(String hosCode,
                                     String depCode,
                                     String workDate) throws ParseException;
}