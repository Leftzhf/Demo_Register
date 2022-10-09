package com.registration.hospital.controller;

import com.registration.hospital.service.ScheduleService;
import com.registration.hospital.vo.service_hospital.BookingScheduleRuleVO;
import com.registration.hospital.vo.service_hospital.ScheduleVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@Api(tags = "排班管理")
@RequestMapping("/admin/hospital/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @GetMapping("/booking/pages/{page}/{limit}")
    @ApiOperation("获取可预约的科室信息-分页")
    public BookingScheduleRuleVO getScheduleList(
                                @PathVariable Long page,
                               @PathVariable Long limit,
                               @RequestParam("hoscode") String hosCode,
                               @RequestParam("depcode") String depCode) {
        return scheduleService.getBookingScheduleList(page, limit,hosCode,depCode);
    }

    @GetMapping("/lists")
    @ApiOperation("获取排班详情")
    public ScheduleVO getScheduleList(@RequestParam("hoscode") String hosCode,
                                            @RequestParam("depcode") String depCode,
                                            @RequestParam("workDate") String workDate) throws ParseException {
        return scheduleService.getScheduleList(hosCode,depCode,workDate);

    }
}
