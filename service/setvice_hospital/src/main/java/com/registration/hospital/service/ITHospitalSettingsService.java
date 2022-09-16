package com.registration.hospital.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.registration.hospital.entity.THospitalSettings;
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
    public Boolean addHospitalSettings(THospitalSettings tHospitalSettings);

    public Page<THospitalSettings> findAllHospitalByPage(Integer current,
                                                         Integer limit,
                                                         HospitalSetQueryVo hospitalSetQueryVo);
    public Boolean sendHospitalSettingsKey(String id);
}
