package com.registration.response.web;

import com.registration.response.functional.ResponseDataBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseController {
    private transient Logger logger = LoggerFactory.getLogger(this.getClass());

    public  <T> ResponseData<T> buildResponseData(ResponseDataBuilder<T> responseDataBuilder, String operate, int... pageInfo){
        return responseDataBuilder.build(operate,pageInfo);
    }


}
