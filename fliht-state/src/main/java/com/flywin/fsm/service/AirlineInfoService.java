package com.flywin.fsm.service;

import com.flywin.core.service.MybatisBaseService;
import com.flywin.fsm.db.entity.AirlineInfo;
import com.flywin.fsm.db.mapper.AirlineInfoMapper;
import org.springframework.stereotype.Service;

/**
 * @program: dioc-flight-state
 * @description: 航空公司信息
 * @author: tongdanping
 * @create: 2023-04-25 16:50
 **/
@Service
public class AirlineInfoService extends MybatisBaseService<AirlineInfoMapper, AirlineInfo> {
}
