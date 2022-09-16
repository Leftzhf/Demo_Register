package com.registration.hospital.controller;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * thospital设置控制器
 * <p>
 * 前端控制器
 * </p>
 *
 * @author author
 * @date 2022/09/16
 * @since 2022-09-12
 */
@RestController
@RequestMapping("/admin/hospital-settings")
@Api(tags="医院设置信息接口",description = "医院设置信息接口")
public class THospitalSettingsController extends BaseController {

    /**
     * 我医院服务
     */
    @Autowired
    private ITHospitalSettingsService iHospitalSettingsService ;

    /**
     * 找到所有hostpital
     *
     * @return {@link ResponseData}<{@link List}<{@link THospitalSettings}>>
     */

    @ApiOperation(value = "查找所有医院设置信息")
    @GetMapping(value = "/findAll")
    public ResponseData<List<THospitalSettings>> findAllHostpital(){
        return buildResponseData(()->{
           return iHospitalSettingsService.list();
        },"查找所有医院设置信息");
    }

    /**
     * 删除医院通过id
     * 删除通过id
     *
     * @param id id
     * @return boolean
     */
    @Transactional(rollbackFor = Exception.class)
    @ApiOperation(value= "根据主键删除医院设置信息")
    @DeleteMapping (value = "/delete/{id}")
    public ResponseData<Boolean> deleteHospitalSettingsById(@PathVariable String id){
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
           return  iHospitalSettingsService.findAllHospitalByPage(current,limit,hospitalSetQueryVo);
        },"查找所有医院设置信息-分页");
    }

    /**
     * 医院设置添加
     *
     * @param tHospitalSettings t医院
     * @return {@link ResponseData}<{@link Boolean}>
     */
    @ApiOperation("添加医院设置信息")
    @PostMapping("/add")
    public ResponseData<Boolean> addHospitalSettings(@RequestBody THospitalSettings tHospitalSettings){
        return buildResponseData(()->{
            return iHospitalSettingsService.addHospitalSettings(tHospitalSettings);
        },"添加医院设置信息");
    }

    /**
     * 通过id获取医院
     *
     * @param id id
     * @return {@link ResponseData}<{@link THospitalSettings}>
     */
    @ApiOperation("根据id查询医院设置信息")
    @GetMapping("/get/{id}")
    public ResponseData<THospitalSettings> getHospitalSettingsById(@PathVariable String id){
       return buildResponseData(()->{
           return iHospitalSettingsService.getById(id);
       },"根据id查询医院设置信息");
    }

    /**
     * 更新医院通过id
     *
     * @param tHospitalSettings t医院
     * @return {@link ResponseData}<{@link Boolean}>
     */
    @ApiOperation("根据id更新医院设置信息")
    @PutMapping("/update")
    public ResponseData<Boolean> updateHospitalSettingsById(@RequestBody THospitalSettings tHospitalSettings){
        return buildResponseData(()->{
            return iHospitalSettingsService.updateById(tHospitalSettings);
        },"根据id更新医院设置信息");
    }

    /**
     * 批量删除医院
     *
     * @param tHospitalSettingsIds t医院id
     * @return {@link ResponseData}<{@link Boolean}>
     */
    @ApiOperation("根据id批量删除医院设置信息")
    @DeleteMapping("/batchDelete")
    public ResponseData<Boolean> batchDeleteHospitalSettings(@RequestBody List<String> tHospitalSettingsIds){
        return buildResponseData(()->{
            return iHospitalSettingsService.removeBatchByIds(tHospitalSettingsIds);
        },"根据id批量删除医院设置信息");
    }
    @PostMapping("sendKey/{id}")
    public ResponseData<Boolean> sendHospitalSettingsKey(@PathVariable String id){
        return  buildResponseData(()->{
            return iHospitalSettingsService.sendHospitalSettingsKey(id);
        },"发送医院接口token");

    }
}