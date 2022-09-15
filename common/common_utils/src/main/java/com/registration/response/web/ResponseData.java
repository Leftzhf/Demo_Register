package com.registration.response.web;

import com.registration.response.enums.ExceptionCodeEnum;
import com.registration.response.exception.ApplicationException;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "请求统一返回数据")
public class ResponseData<T> {
    private static final String SUCCESS= ExceptionCodeEnum.NONE.getCode();

    @ApiModelProperty(value = "请求会返回码：成功返回0000，失败返回负整数")
    private String code=SUCCESS;
    @ApiModelProperty(value = "请求返回消息")
    private String codeMsg;
    @ApiModelProperty(value = "请求返回数据，返回任意类型的对象或数组（对象为空时不返回该字段，数组为空返回0长度的数组）")
    private T data;
    @ApiModelProperty(value = "分页请求信息")
    private ResponsePageData page;
    @ApiModelProperty(value = "操作")
    private String operate;

    public static <T> ResponseData<T> fail(Throwable throwable) {
        ResponseData<T> responseData = new ResponseData<>();
        if(throwable instanceof ApplicationException){
            ApplicationException applicationException = (ApplicationException)throwable;
            responseData.setCode(applicationException.getCode());
        }else{
            responseData.setCode(ExceptionCodeEnum.SYSTEM.getCode());
        }
        responseData.setCodeMsg(throwable.getMessage());
        return responseData;
    }


}
