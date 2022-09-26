package com.registration.hospital.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.registration.hospital.entity.hospital.THospitalSettings;
import com.registration.hospital.vo.query.HospitalSetQueryVo;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author leftleft
 * @since 2022-09-15
 */
public interface ITHospitalSettingsService extends IService<THospitalSettings> {
    Boolean addHospitalSettings(THospitalSettings tHospitalSettings);

    Page<THospitalSettings> findAllHospitalByPage(Integer current,
                                                         Integer limit,
                                                         HospitalSetQueryVo hospitalSetQueryVo);
    Boolean sendHospitalSettingsKey(Long id);

   THospitalSettings getByhospitalCode(String hospitalCode);
}
