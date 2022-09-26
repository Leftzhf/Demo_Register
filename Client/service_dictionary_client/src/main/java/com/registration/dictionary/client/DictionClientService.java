package com.registration.dictionary.client;


import com.registration.response.web.ResponseData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "service-dictionary")
public interface DictionClientService {

    @GetMapping("/admin/dictionary/getName/{dictCode}/{value}")
    ResponseData<String> getNameByDictCodeAndValue (@PathVariable("dictCode") String dictCode,
                                            @PathVariable("value") String value);

    @GetMapping("/admin/dictionary/getName/{dictCode}/{value}")
    ResponseData<String> getNameByValue (@PathVariable("value") String value);
}
