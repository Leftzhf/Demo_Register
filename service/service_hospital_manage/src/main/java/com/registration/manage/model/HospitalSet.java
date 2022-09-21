package com.registration.manage.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * HospitalSet
 * </p>
 *
 * @author qy
 */
@Data
@ApiModel(description = "t_hospital_settings")
@TableName("t_hospital_settings")
public class HospitalSet extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	@ApiModelProperty("医院编号")
	private String hospitalCode;

	@ApiModelProperty(value = "签名秘钥")
	private String signKey;

	@ApiModelProperty(value = "api基础路径")
	private String apiUrl;

}

