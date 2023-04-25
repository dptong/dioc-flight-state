package com.flywin.fsm.db.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.flywin.core.repository.mybaitis.MybatisBaseEntity;
import lombok.Data;

/**
 * @program: dioc-flight-state
 * @description: 航空公司基础信息
 * @author: tongdanping
 * @create: 2023-04-25 15:36
 **/
@Data
@TableName("aifd_airline_info")
public class AirlineInfo extends MybatisBaseEntity {
    /**
     * @desc 自增主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * @desc 简称
     */
    @TableField("short_name")
    private String shortName;

    /**
     * @desc 全称
     */
    @TableField("airline_name")
    private String airlineName;

    /**
     * @desc IATA代码
     */
    @TableField("iata")
    private String iata;

    /**
     * @desc ICAO代码
     */
    @TableField("icao")
    private String icao;
}
