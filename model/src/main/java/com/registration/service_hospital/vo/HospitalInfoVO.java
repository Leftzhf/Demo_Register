package com.registration.service_hospital.vo;

import com.registration.service_hospital.entity.hospital.Hospital;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("医院详情详情VO")
public class HospitalInfoVO {

    @ApiModelProperty("医院详情数据")
    Hospital hospital;


}
