package com.registration.hospital.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.registration.hospital.entity.THospitalSettings;
import com.registration.hospital.service.ITHospitalSettingsService;
import com.registration.hospital.vo.query.HospitalSetQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
@Validated
@CrossOrigin
@RequestMapping("/admin/hospital-settings")
@Api(tags = "医院设置信息接口", description = "医院设置信息接口")
public class THospitalSettingsController {

    /**
     * 我医院服务
     */
    @Autowired
    private ITHospitalSettingsService iHospitalSettingsService;

    /**
     * 找到所有hostpital
     *
     * @return {@link List}<{@link THospitalSettings}>
     */

    @ApiOperation(value = "查找所有医院设置信息")
    @GetMapping(value = "/get")
    public List<THospitalSettings> findAllHostpital() {
        return iHospitalSettingsService.list();
    }

    /**
     * 删除医院通过id
     * 删除通过id
     *
     * @param id id
     * @return boolean
     */
    @Transactional(rollbackFor = Exception.class)
    @ApiOperation(value = "根据主键删除医院设置信息")
    @DeleteMapping(value = "/delete/{id}")
    public Boolean deleteHospitalSettingsById(@PathVariable String id) {
        return iHospitalSettingsService.removeById(id);


    }

    /**
     * 找到所有医院页面
     *
     * @param current            当前页
     * @param limit              限制每页数据数量
     * @param hospitalSetQueryVo 医院设置信息查询条件
     * @return {@link Page}<{@link THospitalSettings}>
     */
    @ApiOperation(value = "查找所有医院设置信息-分页")
    @ApiImplicitParams({
                        @ApiImplicitParam(
                                name = "current",
                                value = "当前页",
                                required = true,
                                paramType = "path"),
                        @ApiImplicitParam(
                                name = "limit",
                                value = "每页数据数量",
                                required = true,
                                paramType = "path",
                                dataType = "Integer"),})
    @PostMapping(value = "/getWithPage/{current}/{limit}")
    public Page<THospitalSettings> findAllHospitalByPage(@PathVariable  Integer current,
                                                         @PathVariable  Integer limit,
                                                         @RequestBody(required = false)
                                                         HospitalSetQueryVo hospitalSetQueryVo) {
        return iHospitalSettingsService.findAllHospitalByPage(current, limit, hospitalSetQueryVo);
    }

    /**
     * 医院设置添加
     *
     * @param tHospitalSettings t医院
     * @return {@link Boolean}
     */
    @ApiOperation("添加医院设置信息")
    @PostMapping("/add")
    public Boolean addHospitalSettings(@RequestBody THospitalSettings tHospitalSettings) {
        return iHospitalSettingsService.addHospitalSettings(tHospitalSettings);
    }

    /**
     * 通过id获取医院
     *
     * @param id id
     * @return {@link THospitalSettings}
     */
    @ApiOperation("根据id查询医院设置信息")
    @GetMapping("/get/{id}")
    public THospitalSettings getHospitalSettingsById(@PathVariable @NotNull Long id) {
        return iHospitalSettingsService.getById(id);
    }

    /**
     * 更新医院通过id
     *
     * @param tHospitalSettings t医院
     * @return {@link Boolean}
     */
    @ApiOperation(value = "根据id更新医院设置信息",notes = "注意，此接口会更新所有传入的参数！")
    @PutMapping("/update")
    public Boolean updateHospitalSettingsById( @RequestBody @Valid THospitalSettings tHospitalSettings) {
        return iHospitalSettingsService.updateById(tHospitalSettings);
    }

    /**
     * 批量删除医院
     *
     * @param tHospitalSettingsIds t医院id
     * @return {@link Boolean}
     */
    @ApiOperation("根据id批量删除医院设置信息")
    @DeleteMapping("/batchDelete")
    public Boolean batchDeleteHospitalSettings(@Validated @RequestBody List<String> tHospitalSettingsIds) {
        return iHospitalSettingsService.removeBatchByIds(tHospitalSettingsIds);
    }

    /**
     * 送医院关键
     *
     * @param id id
     * @return {@link Boolean}
     */
    @ApiOperation("发送医院接口token")
    @PostMapping("/sendKey/{id}")
    public Boolean sendHospitalSettingsKey(@PathVariable @NotNull Long id) {
        return iHospitalSettingsService.sendHospitalSettingsKey(id);
    }
}