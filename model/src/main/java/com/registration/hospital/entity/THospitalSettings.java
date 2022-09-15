package com.registration.hospital.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author leftleft
 * @since 2022-09-15
 */
@Getter
@Setter
@TableName("t_hospital_settings")
public class THospitalSettings implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private String id;

    /**
     * 医院名称
     */
    private String hospitalName;

    /**
     * 医院编号
     */
    private String hospitalCode;

    /**
     * api基础路径
     */
    private String apiUrl;

    /**
     * 签名密钥
     */
    private String signKey;

    /**
     * 联系人
     */
    private String contactsName;

    /**
     * 联系人手机
     */
    private String contactsPhone;

    /**
     * 状态
     */
    private String status;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;
}
