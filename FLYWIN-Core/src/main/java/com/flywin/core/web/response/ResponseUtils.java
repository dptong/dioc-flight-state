package com.flywin.core.web.response;

import com.flywin.core.exception.ErrCodeEnum;

/**
 * @Description: TODO
 * @author: 曾明辉
 * @date: 2019年8月14日
 */
public final class ResponseUtils {

    /**
     * @Title:
     * @Description:
     */
    private ResponseUtils() {
    }

    /**
     * @param <T>  泛型对象
     * @param data 数据
     * @return ResponseResult<T>
     * @Title success
     * @Description 成功有返回值
     * @author 曾明辉
     * @date: 2019年8月14日
     */
    public static <T> ResponseResult<T> success(T data) {
        ResponseResult<T> result = new ResponseResult<T>();
        result.setResCode(0);
        result.setResMsg("OK");
        result.setResData(data);
        return result;
    }

    /**
     * @param <T> 泛型对象
     * @return ResponseResult<T>
     * @Title success
     * @Description 成功无返回值
     * @author 曾明辉
     * @date: 2019年8月14日
     */
    public static <T> ResponseResult<T> success() {
        ResponseResult<T> result = new ResponseResult<T>();
        result.setResCode(0);
        result.setResMsg("OK");
        return result;
    }

    /**
     * @param errMsg 错误信息
     * @param <T>    泛型
     * @return com.flywin.core.web.response.ResponseResult<T>
     * @Description 错误返回
     * @Author wumin
     * @Date 2020-03-18 16:04
     */
    public static <T> ResponseResult<T> fail(String errMsg) {
        ResponseResult<T> result = new ResponseResult<T>();
        result.setResCode(ErrCodeEnum.BUSINESS.getCode());
        result.setResMsg(errMsg);
        return result;

    }

    /**
     * @param <T>     泛型对象
     * @param errCode 错误代码
     * @param errMsg  错误信息
     * @return ResponseResult<T>
     * @Title fail
     * @Description 错误返回
     * @author 曾明辉
     * @date: 2019年8月14日
     */
    public static <T> ResponseResult<T> fail(int errCode, String errMsg) {
        ResponseResult<T> result = new ResponseResult<T>();
        result.setResCode(errCode);
        result.setResMsg(errMsg);
        return result;

    }

}
