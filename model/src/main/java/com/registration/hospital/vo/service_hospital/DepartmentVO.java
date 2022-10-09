package com.registration.hospital.vo;

import com.registration.hospital.entity.hospital.Department;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel("科室管理VO")
@Data
public class DepartmentVO {
    @ApiModelProperty("大科室名称")
    String depname;
    @ApiModelProperty("大科室代码")
    String parementDepartmentCode;
    @ApiModelProperty("子科室列表")
    List<Department> departmentList;
}
