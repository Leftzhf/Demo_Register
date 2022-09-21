package com.registration.dictionary.service;

import com.registration.hospital.entity.TDictionary;
import com.baomidou.mybatisplus.extension.service.IService;
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

    public List<TDictionary> getChildData(String id);

    public void exportDictionary(HttpServletResponse response) throws IOException;

    public void importDictionary(MultipartFile file);
}
