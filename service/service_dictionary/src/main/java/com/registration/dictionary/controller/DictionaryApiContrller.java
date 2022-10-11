package com.registration.dictionary.controller;

import com.registration.dictionary.service.ITDictionaryService;
import com.registration.service_hospital.entity.TDictionary;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/api/dictionary")
@Api(tags = "数据字典服务Api接口",description = "数据字典服务接口")
public class DictionaryApiContrller {

    @Autowired
    private ITDictionaryService itdictionaryService;

    @ApiOperation(value = "根据id获取子节点数据")
    @GetMapping("/getChildById/{id}")
    public List<TDictionary> getChildData(@PathVariable String id){
        return itdictionaryService.getChildData(id);
    }

    @ApiOperation(value = "根据dictCode获取子节点数据")
    @GetMapping("/getChildByDictCode/{dictCode}")
    public List<TDictionary> getChildeDictionaryByDictCode(@PathVariable String dictCode){
        return itdictionaryService.getChildeDictionaryByDictCode(dictCode);
    }
}
