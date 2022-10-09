package com.registration.hospital.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.registration.common.helper.helper.HttpRequestHelper;
import com.registration.dictionary.client.DictionClientService;
import com.registration.hospital.entity.hospital.Hospital;
import com.registration.hospital.reposotory.HospitalRepository;
import com.registration.hospital.service.HospitalService;
import com.registration.hospital.service.ITHospitalSettingsService;
import com.registration.hospital.vo.query.HospitalQueryVo;
import com.registration.response.web.ResponseData;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;
@Service
public class HospitalServiceImpl implements HospitalService {

    @Autowired
    private DictionClientService dictionaryClientService;
    @Autowired
    private HospitalRepository hospitalRepository;

    @Autowired
    private ITHospitalSettingsService hospitalSettingsService;


    @Override
    public Boolean saveHospital(HttpServletRequest request) {
        //从请求体中拿到参数键值对
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, Object> stringObjectMap = HttpRequestHelper.switchMap(parameterMap);
        String signs = (String) stringObjectMap.get("sign");
        String hoscodes = (String) stringObjectMap.get("hoscode");
        //JSON转换为JSON字符串
        String mapString = JSONObject.toJSONString(stringObjectMap);
        //把键值对转换成对象
        Hospital hospital = JSONObject.parseObject(mapString, Hospital.class);
        String logoData = hospital.getLogoData();
        logoData = logoData.replaceAll(" ","+");
        hospital.setLogoData(logoData);
        //判断相同医院编码的数据是否已存有，调用MongoRepository的Api存入Mongodb更新或者新增
        Hospital hospitalByHoscode = hospitalRepository.getHospitalByHoscode(hospital.getHoscode());
        if (hospitalByHoscode == null) {
            hospital.setStatus(0);
            hospital.setCreateTime(new Date());
            hospital.setUpdateTime(new Date());
            hospital.setIsDeleted(0);
            hospitalRepository.save(hospital);
        } else {
            hospitalByHoscode.setUpdateTime(new Date());
            hospitalByHoscode.setIsDeleted(0);
            hospitalByHoscode.setStatus(0);
            hospitalRepository.save(hospitalByHoscode);
        }
        return true;
    }

    @Override
    public Hospital getHospital(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        String hoscode = parameterMap.get("hoscode")[0];
        return hospitalRepository.getHospitalByHoscode(hoscode);
    }

    @Override
    public Page<Hospital> getHospitalByPage(Integer page, Integer limit, HospitalQueryVo hospitalSetQueryVo) {

        PageRequest pageRequest = PageRequest.of(page-1, limit);
        ExampleMatcher matcher = ExampleMatcher.matching().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING).withIgnoreCase(true);
        Hospital hospital = new Hospital();
        BeanUtils.copyProperties(hospitalSetQueryVo,hospital);
        Example<Hospital> example = Example.of(hospital, matcher);
        Page<Hospital> all = hospitalRepository.findAll(example, pageRequest);
        //mongodb查出来的数据是没有医院等级信息的，需要从数据字典获取，这里远程调用数据字典服务的接口获取医院等级信息。
       all.getContent().forEach(iterm->{
            getHospitalInfo(iterm);
       });
       return all;
    }
    public void getHospitalInfo(Hospital hospital){
        //获取医院等级
        ResponseData responseHostype = dictionaryClientService.getNameByDictCodeAndValue("Hostype", hospital.getHostype());
        //查询省 市  地区
        ResponseData provinceString = dictionaryClientService.getNameByValue(hospital.getProvinceCode());
        ResponseData cityString = dictionaryClientService.getNameByValue(hospital.getCityCode());
        ResponseData districtString = dictionaryClientService.getNameByValue(hospital.getDistrictCode());

        hospital.getParam().put("Hostype",responseHostype.getData());
        hospital.getParam().put("fullAddress", provinceString.getData() +(String)cityString.getData()+ districtString.getData());
    }

    @Override
    public Boolean updateHospitalStatus(String id, Integer status) {
        Hospital hospital = hospitalRepository.findById(id).get();
        hospital.setStatus(status);
        hospitalRepository.save(hospital);
        return true;
    }

    @Override
    public Hospital getHospitalById(String id) {
        Hospital hospital = hospitalRepository.findById(id).get();
        getHospitalInfo(hospital);
        return hospital;
    }


}