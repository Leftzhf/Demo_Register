package com.registration.hospital.vo.service_dictionary;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class DictionaryExcelVO {
    @ExcelProperty(value = "id",index = 0)
    private String id;
    @ExcelProperty(value = "父级id",index = 1)
    private String parentId;
    @ExcelProperty(value = "名称",index = 2)
    private String name;
    @ExcelProperty(value = "值",index = 3)
    private String value;
    @ExcelProperty(value = "编码",index = 4)
    private String dictCode;
}
