package com.registration.hospital.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.registration.hospital.entity.hospital.THospitalSettings;
import com.registration.hospital.vo.query.HospitalSetQueryVo;

/**
 * ithospital设置服务
 * <p>
 * 服务类
 * </p>
 *
 * @author leftleft
 * @date 2022/10/09
 * @since 2022-09-15
 */
public interface ITHospitalSettingsService extends IService<THospitalSettings> {
    /**
     * 添加医院设置信息
     *
     * @param tHospitalSettings t医院
     * @return {@link Boolean}
     */
    Boolean addHospitalSettings(THospitalSettings tHospitalSettings);

    /**
     * 查询所有医院设置信息-分页
     *
     * @param current            当前
     * @param limit              限制
     * @param hospitalSetQueryVo 医院设置查询签证官
     * @return {@link Page}<{@link THospitalSettings}>
     */
    Page<THospitalSettings> findAllHospitalByPage(Integer current,
                                                         Integer limit,
                                                         HospitalSetQueryVo hospitalSetQueryVo);

    /**
     * 发送医院token
     *
     * @param id id
     * @return {@link Boolean}
     */
    Boolean sendHospitalSettingsKey(Long id);

    /**
     * 得到byhospital编码
     *
     * @param hospitalCode 医院代码
     * @return {@link THospitalSettings}
     */
    THospitalSettings getByhospitalCode(String hospitalCode);
}
