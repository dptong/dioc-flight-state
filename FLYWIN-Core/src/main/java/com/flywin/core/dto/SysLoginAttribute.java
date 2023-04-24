package com.flywin.core.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.flywin.core.web.jsonserial.CustomTimeSerializer;
import com.flywin.core.web.jsonserial.DateDeserializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: SysLoginAttribute
 * @Description: 登录属性
 * @Author: 冉小刚
 * @Date: 2021-4-9 09:49:01
 * @Version: 2.0
 */
@Data
@ApiModel(description = "登录属性")
public class SysLoginAttribute implements Serializable {

    /**
     * 序列化
     */
    private static final long serialVersionUID = -1392036166051633735L;

    /**
     * 用户账号
     */
    @ApiModelProperty(value = "用户账号", notes = "用户账号")
    private String account;

    /**
     * token
     */
    @ApiModelProperty(value = "token", notes = "token（正式环境：CAS认证后返回的access_token）")
    private String token;

    /**
     * 登录终端
     */
    @ApiModelProperty(value = "登录终端：0.PC端，1.供方APP，2需方APP", notes = "登录终端：0.PC端，1.供方APP，2需方APP")
    private String equipmentCode;

    /**
     * 登录时间
     */
    @ApiModelProperty(value = "登录时间", notes = "登录时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = CustomTimeSerializer.class)
    @JsonDeserialize(using = DateDeserializer.class)
    private Date loginTime;

    /**
     * 通道
     */
//    @ApiModelProperty(value = "通道", notes = "通道")
//    private String chanel;

}
