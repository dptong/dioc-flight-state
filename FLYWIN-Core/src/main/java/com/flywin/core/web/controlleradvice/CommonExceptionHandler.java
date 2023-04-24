package com.flywin.core.web.controlleradvice;

import com.flywin.core.exception.BusinessException;
import com.flywin.core.exception.ErrCodeEnum;
import com.flywin.core.web.response.ResponseResult;
import com.flywin.core.web.response.ResponseUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.security.auth.message.AuthException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

/**
 * <li>类型名称：
 * <li>说明：统一异常处理
 * <li>创建人： 曾明辉
 * <li>创建日期：2018年10月31日
 * <li>修改人：
 * <li>修改日期：
 */
@ControllerAdvice
public class CommonExceptionHandler {

    /**
     * 日志
     */
    private final Logger logger = LoggerFactory.getLogger(CommonExceptionHandler.class);


    /**
     * @param e   异常
     * @param <T> 泛型
     * @return
     * @return ResponseResult
     * @Title exceptionHandler
     * @Description
     * @author 曾明辉
     * @date: 2019年8月14日
     */
    @ExceptionHandler(AuthException.class)
    @ResponseBody
    public <T> ResponseResult<T> exceptionHandler(AuthException e) {
        return ResponseUtils.fail(ErrCodeEnum.AUTH.getCode(), e.getMessage());
    }

    /**
     * @param e   异常
     * @param <T> 泛型
     * @return ResponseResult
     * @Title exceptionHandler
     * @Description
     * @author 曾明辉
     * @date: 2019年8月14日
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public <T> ResponseResult<T> exceptionHandler(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        String errorMesssage = "数据无效：";
        try {
            errorMesssage = "common.exception.data.invalid";
        } catch (Exception ex) {
            logger.error(errorMesssage, ex);
        }

        StringBuffer buf = new StringBuffer();
        int i = 0;
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            if (i > 0) {
                buf.append("；");
            }
            buf.append(fieldError.getDefaultMessage());

            i++;
        }

        errorMesssage += buf.toString();

        ResponseResult<T> result = ResponseUtils.fail(ErrCodeEnum.DATACHECK.getCode(), errorMesssage);
        return result;
    }

    /**
     * @param exception
     * @return com.flywin.core.web.response.ResponseResult<java.lang.String>
     * @Description 数据校验异常处理（针对集合）
     * @Author wumin
     * @Date 2020-07-17 18:12
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public ResponseResult<String> violationExceptionHandle(ConstraintViolationException exception) {
        Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();
        StringBuilder builder = new StringBuilder();
        for (ConstraintViolation violation : violations) {
            if (builder.length() > 0) {
                builder.append(";");
            }
            builder.append(violation.getMessage());
        }
        return ResponseUtils.fail(ErrCodeEnum.DATACHECK.getCode(), builder.toString());
    }

    /**
     * @param e   业务异常
     * @param <T> 泛型
     * @return
     * @return ResponseResult
     * @Title exceptionHandler
     * @Description
     * @author 曾明辉
     * @date: 2019年8月14日
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public <T> ResponseResult<T> exceptionHandler(BusinessException e) {
        logger.warn(e.getErrMsg());
        if (StringUtils.isNotEmpty(e.getErrMsg())) {
            logger.warn("BusinessException异常没有设置错误提示：", e);
        }
        ResponseResult<T> result = ResponseUtils.fail(e.getErrCode(), e.getErrMsg());
        return result;
    }

    /**
     * @param e
     * @return com.flywin.core.web.response.ResponseResult<T>
     * @Description 业务异常参数校验
     * @Author wumin
     * @Date 2020-05-28 9:38
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public <T> ResponseResult<T> exceptionHandler(IllegalArgumentException e) {

        return ResponseUtils.fail(ErrCodeEnum.BUSINESS.getCode(), e.getMessage());
    }

    /**
     * @param e   业务异常
     * @param <T> 泛型
     * @return
     * @return ResponseResult
     * @Title exceptionHandler
     * @Description TODO
     * @author 曾明辉
     * @date: 2019年8月14日
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public <T> ResponseResult<T> exceptionHandler(Exception e) {
        logger.error("system error:", e);
        Throwable cause = this.getThrowableCause(e);
        String msg = cause.getMessage();
        if (StringUtils.isEmpty(msg)) {
            msg = "程序异常";
        }
        return ResponseUtils.fail(ErrCodeEnum.THROWABLE.getCode(), msg);
    }

    /**
     * @param throwable 异常
     * @return Throwable
     * @Title getThrowableCause
     * @Description
     * @author 曾明辉
     * @date: 2019年8月14日
     */
    private Throwable getThrowableCause(Throwable throwable) {
        if (throwable.getCause() != null) {
            return getThrowableCause(throwable.getCause());
        } else {
            return throwable;
        }
    }
}
