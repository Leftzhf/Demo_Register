package com.registration.hospital.service.impl;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.registration.hospital.entity.THospitalSettings;
import com.registration.hospital.mapper.THospitalSettingsMapper;
import com.registration.hospital.service.ITHospitalSettingsService;
import com.registration.hospital.vo.query.HospitalSetQueryVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author leftleft
 * @since 2022-09-15
 */
@Service
public class THospitalSettingsServiceImpl extends ServiceImpl<THospitalSettingsMapper, THospitalSettings> implements ITHospitalSettingsService {
    @Override
    public Boolean addHospitalSettings(THospitalSettings tHospitalSettings) {
        tHospitalSettings.setStatus("1");
        Random random = new Random();
        tHospitalSettings.setSignKey(SecureUtil.md5(LocalDateTime.now() + "" + random.nextInt(1000)));
        return this.save(tHospitalSettings);
    }

    @Override
    public Page<THospitalSettings> findAllHospitalByPage(Integer current, Integer limit, HospitalSetQueryVo hospitalSetQueryVo) {
        Page<THospitalSettings> page = new Page<>(current, limit);
        QueryWrapper<THospitalSettings> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(hospitalSetQueryVo.getHospitalName())) {
            wrapper.like("hospital_name", hospitalSetQueryVo.getHospitalName());
        }
        if (StringUtils.isNotBlank(hospitalSetQueryVo.getHospitalCode())) {
            wrapper.eq("hospital_code", hospitalSetQueryVo.getHospitalCode());
        }
        return this.page(page, wrapper);
    }

    @Override
    public Boolean sendHospitalSettingsKey(String id) {
        //TODO 发送医院接口token
        return null;
    }
}
