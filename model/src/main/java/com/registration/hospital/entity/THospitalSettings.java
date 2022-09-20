package com.registration.hospital.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author leftleft
 * @since 2022-09-16
 */
@Data
@TableName("t_hospital_settings")
@ApiModel(value = "THospitalSettings对象", description = "")
public class THospitalSettings implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键id")
    @TableId(type = IdType.ASSIGN_ID)
    @NotBlank(message = "主键id不能为空")
    private String id;

    @ApiModelProperty("医院名称")
    private String hospitalName;

    @ApiModelProperty("医院编号")
    private String hospitalCode;

    @ApiModelProperty("api基础路径")
    private String apiUrl;

    @ApiModelProperty("签名密钥")
    private String signKey;

    @ApiModelProperty("联系人")
    private String contactsName;

    @ApiModelProperty("联系人手机")
    private String contactsPhone;

    @ApiModelProperty("状态")
    private String status;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty("修改时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "逻辑删除(1:已删除，0:未删除)")
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Integer isDeleted;
}
