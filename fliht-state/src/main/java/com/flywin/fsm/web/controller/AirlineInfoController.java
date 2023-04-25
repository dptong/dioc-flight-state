package com.flywin.fsm.web.controller;

import com.flywin.core.web.MBaseController;
import com.flywin.core.web.response.ResponseResult;
import com.flywin.core.web.response.ResponseUtils;
import com.flywin.fsm.db.entity.AirlineInfo;
import com.flywin.fsm.db.mapper.AirlineInfoMapper;
import com.flywin.fsm.service.AirlineInfoService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: dioc-flight-state
 * @description: 基础信息相关接口
 * @author: tongdanping
 * @create: 2023-04-25 15:25
 **/
@RestController("/fsm/airline")
public class AirlineInfoController extends MBaseController<AirlineInfo, AirlineInfoMapper, AirlineInfoService> {
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public ResponseResult<AirlineInfo> get(@PathVariable("id") String id) throws Exception {
        AirlineInfo entity = this.baseService.get(id);
        return ResponseUtils.success(entity);
    }

}
