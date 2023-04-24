package com.flywin.fileservers.feign.client;

import com.flywin.core.web.response.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Description: TODO
 * @Author: wl
 * @Date: 2021/2/27 10:43
 * @Version:
 */
@FeignClient(name = "testClient")
public interface AppVersionClient {

    @GetMapping(value = "/test/client")
    ResponseResult<String> testClient(@RequestParam(value = "id") String id) throws Exception;

}
