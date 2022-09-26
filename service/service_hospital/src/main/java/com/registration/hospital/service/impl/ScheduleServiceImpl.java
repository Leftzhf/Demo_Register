package com.registration.hospital.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.registration.common.helper.helper.HttpRequestHelper;
import com.registration.hospital.entity.hospital.Schedule;
import com.registration.hospital.reposotory.ScheduleRepository;
import com.registration.hospital.service.ScheduleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Override
    public Boolean saveSchedule(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, Object> stringObjectMap = HttpRequestHelper.switchMap(parameterMap);
        String signs = (String) stringObjectMap.get("sign");
        String hoscodes = (String) stringObjectMap.get("hoscode");

        String jsonString = JSONObject.toJSONString(stringObjectMap);
        Schedule schedule = JSONObject.parseObject(jsonString, Schedule.class);

        Schedule scheduleId = scheduleRepository.getScheduleByHoscodeAndHosScheduleId(schedule.getHoscode(), schedule.getHosScheduleId());
        if (scheduleId != null) {
            scheduleId.setUpdateTime(new Date());
            scheduleId.setIsDeleted(0);
            scheduleId.setStatus(1);
            scheduleRepository.save(scheduleId);
        } else {
            schedule.setCreateTime(new Date());
            schedule.setIsDeleted(0);
            schedule.setUpdateTime(new Date());
            schedule.setStatus(1);
            scheduleRepository.save(schedule);
        }
        return true;
    }

    @Override
    public Page<Schedule> getSchedule(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, Object> stringObjectMap = HttpRequestHelper.switchMap(parameterMap);
        String jsonString = JSONObject.toJSONString(stringObjectMap);
        Schedule schedule = JSONObject.parseObject(jsonString, Schedule.class);

        int page = StringUtils.isBlank((CharSequence) stringObjectMap.get("page")) ? 1 : Integer.parseInt((String) stringObjectMap.get("page"));
        int limit = StringUtils.isBlank((CharSequence) stringObjectMap.get("limit")) ? 1 : Integer.parseInt((String) stringObjectMap.get("limit"));

        PageRequest pageRequest = PageRequest.of(page - 1, limit);
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase(true);
        Example<Schedule> example = Example.of(schedule, matcher);
        return scheduleRepository.findAll(example, pageRequest);
    }

    @Override
    public Boolean deleteSchedule(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, Object> stringObjectMap = HttpRequestHelper.switchMap(parameterMap);
        String jsonString = JSONObject.toJSONString(stringObjectMap);
        Schedule schedule = JSONObject.parseObject(jsonString, Schedule.class);
        Schedule schedule1 = scheduleRepository.getScheduleByHoscodeAndHosScheduleId(schedule.getHoscode(), schedule.getHosScheduleId());
        if(schedule1 != null){
            scheduleRepository.deleteById(schedule1.getId());
        }
        return true;
    }
}
