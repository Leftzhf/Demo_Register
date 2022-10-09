package com.registration.hospital.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONObject;
import com.registration.common.helper.helper.HttpRequestHelper;
import com.registration.hospital.entity.hospital.Department;
import com.registration.hospital.entity.hospital.Schedule;
import com.registration.hospital.entity.hospital.THospitalSettings;
import com.registration.hospital.reposotory.DepartmentRepository;
import com.registration.hospital.reposotory.ScheduleRepository;
import com.registration.hospital.service.ITHospitalSettingsService;
import com.registration.hospital.service.ScheduleService;
import com.registration.hospital.vo.service_hospital.BookingScheduleRuleListVo;
import com.registration.hospital.vo.service_hospital.BookingScheduleRuleVO;
import com.registration.hospital.vo.service_hospital.ScheduleVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ITHospitalSettingsService hospitalSettingsService;

    @Autowired
    private DepartmentRepository departmentRepository;

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
    @Override
    public Schedule getScheduleById(String id) {
        return null;
    }

    @Override
    public  BookingScheduleRuleVO getBookingScheduleList(Long page, Long limit, String hosCode, String depCode) {
        //1 根据医院编号 和 科室编号 查询
        Criteria criteria = Criteria.where("hoscode").is(hosCode).and("depcode").is(depCode);

        //2 根据工作日workDate期进行分组
        Aggregation agg = Aggregation.newAggregation(
                //匹配条件
                Aggregation.match(criteria),
                //以workDate字段分组查询
                Aggregation.group("workDate")
                        //分组中的第一个workDate字段的值作为workDate字段返回
                        .first("workDate").as("workDate")
                        //3 统计号源数量
                        .count().as("docCount")
                        .sum("reservedNumber").as("reservedNumber")
                        .sum("availableNumber").as("availableNumber"),
                //排序
                Aggregation.sort(Sort.Direction.DESC,"workDate"),
                //4 实现分页
                Aggregation.skip((page-1)*limit),
                Aggregation.limit(limit)
        );
        //调用方法，最终执行
        AggregationResults<BookingScheduleRuleListVo> aggResults =
                mongoTemplate.aggregate(agg, Schedule.class, BookingScheduleRuleListVo.class);
        List<BookingScheduleRuleListVo> bookingScheduleRuleVoList = aggResults.getMappedResults();

        //分组查询的总记录数
        Aggregation totalAgg = Aggregation.newAggregation(
                Aggregation.match(criteria),
                Aggregation.group("workDate")
        );
        AggregationResults<BookingScheduleRuleListVo> totalAggResults =
                mongoTemplate.aggregate(totalAgg, Schedule.class, BookingScheduleRuleListVo.class);
        int total = totalAggResults.getMappedResults().size();
        THospitalSettings hospitalInfo = hospitalSettingsService.getByhospitalCode(hosCode);
        String hospitalName = hospitalInfo.getHospitalName();
        Department departmentIfo = departmentRepository.getDepartmentByHoscodeAndDepcode(hosCode, depCode);
        String depname = departmentIfo.getDepname();
        bookingScheduleRuleVoList.forEach(item->{
            //转成中文日期
            item.setDayOfWeek(DateUtil.dayOfWeekEnum(item.getWorkDate()).toChinese());
        });
        BookingScheduleRuleVO bookingScheduleRuleVo = new BookingScheduleRuleVO();
        bookingScheduleRuleVo.setTotal(total);
        bookingScheduleRuleVo.setDepatmentName(depname);
        bookingScheduleRuleVo.setHospitalName(hospitalName);
        bookingScheduleRuleVo.setBookingScheduleRuleListVoList(bookingScheduleRuleVoList);

        return bookingScheduleRuleVo;
    }

    @Override
    public ScheduleVO getScheduleList(String hosCode, String depCode, String workDate) throws ParseException {
        List<Schedule> schedule = scheduleRepository.findScheduleByHoscodeAndDepcodeAndWorkDate(hosCode, depCode, new DateTime(workDate).toSqlDate());
        ScheduleVO scheduleVo = new ScheduleVO();
        scheduleVo.setScheduleList(schedule);
        return scheduleVo;
    }
}
