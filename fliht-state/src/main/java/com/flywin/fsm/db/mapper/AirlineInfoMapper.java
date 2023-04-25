package com.flywin.fsm.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.flywin.fsm.db.entity.AirlineInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @program: dioc-flight-state
 * @description: 航空公司信息mapper
 * @author: tongdanping
 * @create: 2023-04-25 16:51
 **/
@Mapper
public interface AirlineInfoMapper extends BaseMapper<AirlineInfo> {
}
