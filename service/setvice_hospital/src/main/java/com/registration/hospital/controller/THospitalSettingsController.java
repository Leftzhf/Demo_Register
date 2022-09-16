package com.registration.hospital.controller;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.registration.hospital.entity.THospitalSettings;
import com.registration.hospital.service.ITHospitalSettingsService;
import com.registration.hospital.vo.query.HospitalSetQueryVo;
import com.registration.response.exception.ApplicationException;
import com.registration.response.web.BaseController;
import com.registration.response.web.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

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

    @ApiOperation(value = "查找所有医院设置信息")
    @GetMapping(value = "/findAll")
    public ResponseData<List<THospitalSettings>> findAllHostpital(){
        return buildResponseData(()->{
           return iHospitalSettingsService.list();
        },"查找所有医院设置信息");
    }

    /**
     * 删除通过id
     *
     * @param id id
     * @return boolean
     */
    @Transactional(rollbackFor = Exception.class)
    @ApiOperation(value= "根据主键删除医院设置信息")
    @DeleteMapping (value = "/delete/{id}")
    public ResponseData<Boolean> deleteById(@PathVariable String id){
        return buildResponseData(() ->{
            boolean falg = iHospitalSettingsService.removeById(id);
            if(falg){
                return falg;
            }else {
                throw new ApplicationException("删除失败,id不存在");
            }
        },"根据主键删除医院设置信息");
    }

    /**
     * 找到所有医院页面
     *
     * @param current            当前页
     * @param limit              限制每页数据数量
     * @param hospitalSetQueryVo 医院设置信息查询条件
     * @return {@link ResponseData}<{@link Page}<{@link THospitalSettings}>>
     */
    @ApiOperation(value= "查找所有医院设置信息-分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current",value = "当前页",required = true,paramType = "path"),
            @ApiImplicitParam(name = "limit",value = "每页数据数量",required = true,paramType = "path",dataType = "Integer"),
    })
    @PostMapping(value = "/findAllByPage/{current}/{limit}")
    public ResponseData<Page<THospitalSettings>> findAllHospitalByPage(@PathVariable Integer current,
                                                                       @PathVariable Integer limit,
                                                                       @RequestBody(required = false) HospitalSetQueryVo hospitalSetQueryVo){
        return buildResponseData(()->{
            Page<THospitalSettings> page = new Page<>(current,limit);
            QueryWrapper<THospitalSettings> wrapper = new QueryWrapper<>();
            if (StringUtils.isNotBlank(hospitalSetQueryVo.getHosname())) {
                wrapper.like("hospital_name", hospitalSetQueryVo.getHosname());
            }
            if (StringUtils.isNotBlank(hospitalSetQueryVo.getHoscode())){
                wrapper.eq("hospital_code", hospitalSetQueryVo.getHoscode());
            }
           return  iHospitalSettingsService.page(page, wrapper);
        },"查找所有医院设置信息-分页");
    }

    @ApiOperation("添加医院设置信息")
    @PostMapping("/add")
    public ResponseData<Boolean> addHospitalSettings(@RequestBody THospitalSettings tHospitalSettings){
        return buildResponseData(()->{
            tHospitalSettings.setStatus("1");
            Random random = new Random();
            tHospitalSettings.setSignKey(SecureUtil.md5(LocalDateTime.now()+""+random.nextInt(1000)));
            return iHospitalSettingsService.save(tHospitalSettings);
        },"添加医院设置信息");
    }
}