package com.registration.hospital.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author leftleft
 * @since 2022-09-20
 */
@Getter
@Setter
@TableName("t_dictionary")
@ApiModel(value = "TDictionary对象", description = "")
public class TDictionary implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键id")
    private String id;

    @ApiModelProperty("上级id")
    private String parentId;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("值")
    private String value;

    @ApiModelProperty("编码")
    private String dictCode;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime updateTime;

    @ApiModelProperty("删除标记")
    @TableField(fill = FieldFill.INSERT)
    private String isDeleted;

    @ApiModelProperty("是否存在子节点")
    @TableField(exist = false)
    private boolean hasChildren;
}
