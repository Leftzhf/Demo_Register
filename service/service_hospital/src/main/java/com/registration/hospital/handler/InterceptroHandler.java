package com.registration.hospital.handler;

import cn.hutool.crypto.SecureUtil;
import com.registration.service_hospital.entity.hospital.THospitalSettings;
import com.registration.hospital.service.ITHospitalSettingsService;
import com.registration.response.enums.ResultCodeEnum;
import com.registration.response.exception.ApplicationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 拦截器，对controller请求前后进行AOP切入
 * preHandle – Controller – postHandle – afterCompletion
 *
 * @author leftleft
 * @date 2022/09/23
 */
@Component
public class InterceptroHandler implements HandlerInterceptor {
    @Autowired
    ITHospitalSettingsService hospitalSettingsService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Map<String, String[]> parameterMap = request.getParameterMap();
        String signs = parameterMap.get("sign")[0];
        String hoscodes = parameterMap.get("hoscode")[0];
        THospitalSettings byhospitalCode = hospitalSettingsService.getByhospitalCode(hoscodes);

        String signKey = byhospitalCode.getSignKey();
        String md5 = SecureUtil.md5(signKey);
        if (md5.equals(signs)) {
            return HandlerInterceptor.super.preHandle(request, response, handler);
        } else {
            throw new ApplicationException(ResultCodeEnum.SIGN_ERROR.getCode(),"校验失败，请核对医院签名Key是否正确");
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
