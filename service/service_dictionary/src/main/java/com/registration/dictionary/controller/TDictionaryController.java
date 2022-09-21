package com.registration.dictionary.controller;

import com.registration.hospital.entity.TDictionary;
import com.registration.response.exception.ApplicationException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.registration.dictionary.service.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author leftleft
 * @since 2022-09-20
 */
@RestController
@CrossOrigin
@Api(tags = "数据字典服务接口",description = "数据字典服务接口")
@RequestMapping("/admin/dictionary")
public class TDictionaryController {

    @Autowired
    private ITDictionaryService itdictionaryService;

    @ApiOperation(value = "根据id获取子节点数据")
    @GetMapping("/get/{id}")
    public List<TDictionary> getChildData(@PathVariable String id){
        return itdictionaryService.getChildData(id);
    }
    @ApiOperation(value = "导出数据字典")
    @GetMapping("/exportDictionary")
    public void exportDictionary(HttpServletResponse response) throws ApplicationException, IOException {
         itdictionaryService.exportDictionary(response);
    }
    @ApiOperation(value = "导入数据字典")
    @PostMapping("/importDictionary")
    public void importDictionary(MultipartFile file) throws ApplicationException, IOException {
        itdictionaryService.importDictionary(file);
    }
}
