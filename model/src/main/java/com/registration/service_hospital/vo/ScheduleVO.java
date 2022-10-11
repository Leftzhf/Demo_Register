package com.registration.service_hospital.vo;

import com.registration.service_hospital.entity.hospital.Schedule;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
@Data
@ApiModel("排班详情VO")
public class ScheduleVO {
    @ApiModelProperty("排班详情数据")
    List<Schedule> scheduleList;


}
