package com.registration.hospital.service;

import com.registration.service_hospital.entity.hospital.Hospital;
import com.registration.service_hospital.vo.query.HospitalQueryVo;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * 医院服务
 *
 * @author leftleft
 * @date 2022/10/06
 */
public interface HospitalService {
    /**
     * 保存医院-医院信息管理系统
     *
     * @param request 请求
     * @return {@link Boolean}
     */
    Boolean saveHospital(HttpServletRequest request);

    /**
     * 查询医院-医院信息管理系统
     *
     * @param request 请求
     * @return {@link Hospital}
     */
    Hospital getHospital(HttpServletRequest request);

    /**
     * 查询所有医院信息-分页
     *
     * @param page            页面
     * @param limit           限制
     * @param hospitalQueryVo 医院查询签证官
     * @return {@link Page}<{@link Hospital}>
     */
    Page<Hospital> getHospitalByPage(Integer page,
                                     Integer limit,
                                     HospitalQueryVo hospitalQueryVo);

    /**
     * 更新状态上线状态
     *
     * @param id     id
     * @param status 上线状态
     * @return {@link Boolean}
     */
    Boolean updateHospitalStatus(String id,
                                Integer status);

    /**
     * 通过id获取医院详情
     *
     * @param id id
     * @return {@link Hospital}
     */
    Hospital getHospitalById(String id);

    List<Hospital> getHospitalByName (String name);

    Hospital getHospitalInfo(String hosCode);

}
