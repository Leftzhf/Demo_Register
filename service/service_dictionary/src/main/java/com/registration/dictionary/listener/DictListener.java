package com.registration.dictionary.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.registration.dictionary.mapper.TDictionaryMapper;
import com.registration.hospital.entity.TDictionary;
import com.registration.hospital.vo.DictionaryExcelVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DictListener extends AnalysisEventListener<DictionaryExcelVO> {
    @Autowired
    TDictionaryMapper dictionaryMapper;

    //一行一行读取
    @Override
    public void invoke(DictionaryExcelVO dictEeVo, AnalysisContext analysisContext) {
        //调用方法添加数据库
        TDictionary dict = new TDictionary();
        BeanUtils.copyProperties(dictEeVo,dict);
        dictionaryMapper.insert(dict);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
