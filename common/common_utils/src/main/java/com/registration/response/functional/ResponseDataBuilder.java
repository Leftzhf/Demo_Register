package com.registration.response.functional;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.registration.response.enums.ExceptionCodeEnum;
import com.registration.response.exception.ApplicationException;
import com.registration.response.web.ResponseData;
import com.registration.response.web.ResponsePageData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@FunctionalInterface
public interface ResponseDataBuilder<T> {

    Logger logger = LoggerFactory.getLogger(ResponseDataBuilder.class);

    T invokeData();

    default ResponseData<T> build(String operate, int... pageInfo){
        ResponseData<T> responseData = new ResponseData<>();
        responseData.setOperate(operate);

        try{
            T t = invokeData();
            if(t == null){
                responseData.setCodeMsg(operate);
                return responseData;
            }

            Class pageClass = null;
            try{
                pageClass = Class.forName("com.github.pagehelper.Page");
            }catch (ClassNotFoundException e){
                logger.warn(e.getMessage());
            }
            if(pageClass != null && t instanceof Page){
                Page page = (Page) t;
                ResponsePageData pageData = new ResponsePageData();
                pageData.setTotalCount(page.getTotal());
                responseData.setPage(pageData);
            }
            else if (t instanceof List && pageInfo.length > 1){
                int  limit  = pageInfo[1];
                int  offset = (pageInfo[0]-1)*pageInfo[1];
                List list = (List)t;
                ResponsePageData pageData = new ResponsePageData();
                pageData.setTotalCount(list.size());
                responseData.setPage(pageData);
                if(limit > 0 || offset < list.size()){
                    list = list.subList(offset, Math.min((offset + limit), list.size()));
                }
                t = (T)list;
            }

            responseData.setData(t);
            responseData.setCodeMsg(operate );

        }catch (ApplicationException e){
            logger.error(operate+"失败："+e.getMessage(),e);
            responseData.setCode(e.getCode());
            responseData.setCodeMsg(operate + ": " + e.getMessage());
        }
        catch (Exception e){

            logger.error(operate+"失败："+e.getMessage(),e);
            responseData.setCode(ExceptionCodeEnum.SYSTEM.getCode());
            responseData.setCodeMsg(operate + "!");

        }
        return responseData;
    }
}
