package com.registration.service_hospital.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
@Data
@ApiModel(description = "可预约排班规则")
public class BookingScheduleRuleVO {
    @ApiModelProperty(value = "可预约的排版")
    List<BookingScheduleRuleListVo> bookingScheduleRuleListVoList;

    @ApiModelProperty(value = "总记录数")
    Integer total;

    @ApiModelProperty("科室名称")
    String DepatmentName;

    @ApiModelProperty("医院名称")
    String hospitalName;
}
