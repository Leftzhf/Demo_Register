package com.registration.response.enums;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * 异常代码枚举
 *
 * @author leftleft
 * @date 2022/09/15
 */
@Getter
public enum ExceptionCodeEnum {

    NONE("0000", "无异常"),
    SYSTEM("-1", "系统异常"),
    APPLICATION("-9999", "业务异常"),
    DATE_TRANS("-9998", "日期转换异常"),
    CIRCUIT_BREAKER("-9997", "熔断");


    /**
     * 代码
     */
    private String code;
    /**
     * desc
     */
    private String desc;

    ExceptionCodeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }


    public static ExceptionCodeEnum getByCode(String code) {

        for (ExceptionCodeEnum em : ExceptionCodeEnum.values()) {
            if (StringUtils.equals(em.getCode(), code)) {
                return em;
            }
        }
        return null;

    }

}