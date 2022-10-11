package com.registration.dictionary.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.registration.dictionary.listener.DictListener;
import com.registration.dictionary.mapper.TDictionaryMapper;
import com.registration.dictionary.service.ITDictionaryService;
import com.registration.service_hospital.entity.TDictionary;
import com.registration.service_dictionary.vo.DictionaryExcelVO;
import com.registration.response.exception.ApplicationException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author leftleft
 * @since 2022-09-20
 */
@Service
public class TDictionaryServiceImpl extends ServiceImpl<TDictionaryMapper, TDictionary> implements ITDictionaryService {
    @Autowired
    TDictionaryMapper tDictionaryMapper;

    @Autowired
    DictListener dictListener;

    @Override
    @Cacheable(value = "dict",keyGenerator = "keyGenerator")
    public List<TDictionary> getChildData(String id) {
        QueryWrapper<TDictionary> queryWrap = new QueryWrapper<>();
        queryWrap.eq("parent_id",id);
        List<TDictionary> tDictionaries = tDictionaryMapper.selectList(queryWrap);
        tDictionaries.forEach(node->{
            node.setHasChildren(hasChild(node.getId()));
        });
        return tDictionaries;
    }

    private boolean hasChild(String id){
        QueryWrapper<TDictionary> queryWrap = new QueryWrapper<>();
        queryWrap.eq("parent_id",id);
        Long child = tDictionaryMapper.selectCount(queryWrap);
        if (child==0){
            return false;
        }else {
            return true;
        }
    }

    @Override
    public void exportDictionary(HttpServletResponse response) {
        //使用serlvet的response对象设置返回属性值
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = "dict";
        response.setHeader("Content-disposition", "attachment;filename="+ fileName + ".xlsx");

        List<TDictionary>  Dictionary=tDictionaryMapper.selectList(null);
        List<DictionaryExcelVO> DictionaryExcelVOList = new ArrayList<>();
        Dictionary.forEach(tDictionary -> {
            DictionaryExcelVO dictionaryExcelVO = new DictionaryExcelVO();
            BeanUtil.copyProperties(tDictionary,dictionaryExcelVO);
            DictionaryExcelVOList.add(dictionaryExcelVO);
        });
        try {
            EasyExcel.write(response.getOutputStream(), DictionaryExcelVO.class).sheet("dict").doWrite(DictionaryExcelVOList);
        } catch (IOException e) {
            throw new ApplicationException("文件导出异常！");
        }
    }

    @Override
    @CacheEvict(value = "dict", allEntries=true)
    public void importDictionary(MultipartFile file) {
        try {
            EasyExcel.read(file.getInputStream(), DictionaryExcelVO.class,dictListener).sheet().doRead();
        }catch (IOException e) {
            throw new ApplicationException("上传文件失败");
        }
    }

    @Override
    public String getName(String dictCode, String value) {
        QueryWrapper<TDictionary> queryWrap = new QueryWrapper<>();
        TDictionary tDictionary;
        if(StringUtils.isBlank(dictCode)){
            queryWrap.eq("value",value);
             tDictionary=tDictionaryMapper.selectOne(queryWrap);
        }else {
            queryWrap.eq("dict_code",dictCode);
            TDictionary dictionary = tDictionaryMapper.selectOne(queryWrap);
            String dictionaryId = dictionary.getId();
            QueryWrapper<TDictionary> queryWrap2 = new QueryWrapper<>();
            queryWrap2.eq("parent_id",dictionaryId).eq("value", value);
            tDictionary = tDictionaryMapper.selectOne(queryWrap2);
        }
        return tDictionary.getName();
    }

    @Override
    public List<TDictionary> getChildeDictionaryByDictCode(String dictCode) {

        QueryWrapper<TDictionary> queryWrap = new QueryWrapper<>();
        queryWrap.eq("dict_code",dictCode);
        TDictionary tDictionary = tDictionaryMapper.selectOne(queryWrap);
        String tDictionaryId = tDictionary.getId();
        List<TDictionary> tDictionaries = tDictionaryMapper.selectList(new QueryWrapper<TDictionary>().eq("parent_id", tDictionaryId));
        tDictionaries.forEach(node->{
            node.setHasChildren(hasChild(node.getId()));
        });
        return tDictionaries;
    }
}
