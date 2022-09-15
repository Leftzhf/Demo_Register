package com.registration.response.exception;

import com.registration.response.enums.ExceptionCodeEnum;
import lombok.Data;

@Data
public class ApplicationException extends RuntimeException {

    /**
     * 异常对应的返回码
     */
    private String code = ExceptionCodeEnum.APPLICATION.getCode();
    /**
     * 异常对应的描述信息
     */
    private String msg;

    public ApplicationException() {
        super();
    }

    public ApplicationException(String message) {
        super(message);
        this.msg = message;
    }


    public ApplicationException(String code, String message) {
        super(message);
        this.code = code;
        this.msg = message;
    }



}