/**
 * <li>文件名：ResponseResult.java
 * <li>说明：
 * <li>创建人： 曾明辉
 * <li>创建日期：2018年10月31日
 * <li>修改人：
 * <li>修改日期：
 */
package com.flywin.core.web.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *
 * @Description: TODO
 * @param <T>
 *            泛型
 * @author: 曾明辉
 * @date: 2019年8月14日
 */
@ApiModel(description = "ResponseResult")
@Data
public class ResponseResult<T> {

    /**
     * 返回代码
     */
    @ApiModelProperty(value = "返回代码")
    private int resCode;

    /**
     * 返回信息
     */
    @ApiModelProperty(value = "返回信息")
    private String resMsg;

    /**
     * 详细信息
     */
    @ApiModelProperty(value = "详细信息")
    private String detailMsg;

    /**
     * 返回数据
     */
    @ApiModelProperty(value = "返回数据")
    private T resData;


}
