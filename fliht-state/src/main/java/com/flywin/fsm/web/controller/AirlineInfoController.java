package com.flywin.fsm.web.controller;

import com.flywin.core.mybatis.MBaseController;
import com.flywin.fsm.db.entity.AirlineInfo;
import com.flywin.fsm.db.mapper.AirlineInfoMapper;
import com.flywin.fsm.service.AirlineInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;

/**
 * @program: dioc-flight-state
 * @description: 基础信息相关接口
 * @author: tongdanping
 * @create: 2023-04-25 15:25
 **/
@Controller
@RequestMapping("/fsm/airline")
public class AirlineInfoController extends MBaseController<AirlineInfo, AirlineInfoMapper, AirlineInfoService> {

    @PostConstruct
    private void init(){
        logger.warn("init airline controller");
    }
//
//    @RequestMapping(value = "/get", method = RequestMethod.GET)
//    public ResponseResult<AirlineInfo> get(@RequestParam("id") String id) throws Exception {
//        AirlineInfo entity = (AirlineInfo) this.baseService.get(id);
//        return ResponseUtils.success(entity);
//    }

}
