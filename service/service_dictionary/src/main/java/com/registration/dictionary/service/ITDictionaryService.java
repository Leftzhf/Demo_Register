package com.registration.dictionary.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.registration.service_hospital.entity.TDictionary;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author leftleft
 * @since 2022-09-20
 */
public interface ITDictionaryService extends IService<TDictionary> {

    List<TDictionary> getChildData(String id);

    void exportDictionary(HttpServletResponse response) throws IOException;

    void importDictionary(MultipartFile file);

    String getName (String dictCode,String value);

    List<TDictionary> getChildeDictionaryByDictCode(String dictCode);
}
