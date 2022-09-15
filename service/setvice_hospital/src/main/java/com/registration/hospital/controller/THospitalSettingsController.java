package com.registration.hospital.controller;

import com.registration.hospital.entity.THospitalSettings;
import com.registration.hospital.service.ITHospitalSettingsService;
import com.registration.response.web.BaseController;
import com.registration.response.web.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author author
 * @since 2022-09-12
 */
@RestController
@RequestMapping("/admin/hospital-settings")
@Api(tags="医院设置接口",description = "医院设置接口")
public class THospitalSettingsController extends BaseController {

    @Autowired
    private ITHospitalSettingsService iHospitalSettingsService ;

    /**
     * 找到所有hostpital
     *
     * @return {@link List}<{@link THospitalSettings}>
     */

    @ApiOperation(value = "查找所有医院设置")
    @RequestMapping(value = "/findAll",method = {RequestMethod.GET})
    public ResponseData<List<THospitalSettings>> findAllHostpital(){
        return buildResponseData(()->{
           return iHospitalSettingsService.list();
        },"查找所有医院设置");
    }

    /**
     * 删除通过id
     *
     * @param id id
     * @return boolean
     */
    @ApiOperation(value= "删除")
    @DeleteMapping (value = "/delete/{id}")
    public boolean deleteById(String id){
        return iHospitalSettingsService.removeById(id);
    }
}