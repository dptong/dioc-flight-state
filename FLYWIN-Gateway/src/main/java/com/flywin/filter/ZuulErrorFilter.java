package com.flywin.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flywin.core.message.I18nMessage;
import com.flywin.core.web.response.ResponseResult;
import com.flywin.core.web.response.ResponseUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Slf4j
@Component
public class ZuulErrorFilter extends ZuulFilter {


    /**
     * 国际化消息
     */
    @Autowired
    private I18nMessage i18nMessage;

    /**
     * to classify a filter by type. Standard types in Zuul are "pre" for pre-routing filtering,
     * "route" for routing to an origin, "post" for post-routing filters, "error" for error handling.
     * We also support a "static" type for static responses see  StaticResponseFilter.
     * Any filterType made be created or added and run by calling FilterProcessor.runFilters(type)
     *
     * @return A String representing that type
     */
    @Override
    public String filterType() { // 前置过滤
        return FilterConstants.ERROR_TYPE;
    }

    /**
     * filterOrder() must also be defined for a filter. Filters may have the same  filterOrder if precedence is not
     * important for a filter. filterOrders do not need to be sequential.
     *
     * @return the int order of a filter
     */
    @Override
    public int filterOrder() { // 优先级为0，数字越大，优先级越低
        return FilterConstants.SEND_ERROR_FILTER_ORDER;
    }

    /**
     * a "true" return from this method means that the run() method should be invoked
     *
     * @return true if the run() method should be invoked. false will not invoke the run() method
     */
    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        return ctx.getThrowable() != null;
    }

    /**
     * @return
     * @throws ZuulException
     * @Title run
     * @Description
     * @author 曾明辉
     * @date: 2019年8月15日
     */
    @Override
    public Object run() throws ZuulException {

        RequestContext ctx = RequestContext.getCurrentContext();
        Throwable throwable = ctx.getThrowable();

        HttpServletRequest request = ctx.getRequest();

        Throwable ex = getException(throwable);

        ResponseResult<Object> result = ResponseUtils.fail(CodeConstants.INT_500,
                i18nMessage.getStringMessage("zuul.error.service.connection.refused"));

        if (ex instanceof java.net.ConnectException) { // 服务连接拒绝

            result.setResMsg(this.i18nMessage.getStringMessage("zuul.error.service.connection.refused"));

            log.error("uri:{},error:{}", request.getRequestURI(), ex);
        } else if (ex instanceof java.net.SocketTimeoutException) { // 服务连接超时
            result.setResCode(CodeConstants.INT_504);
            result.setResMsg(this.i18nMessage.getStringMessage("zuul.error.service.connection.timeout"));

            log.error("uri:{},error:{}", request.getRequestURI(), ex);
        } else if (ex instanceof com.netflix.client.ClientException) { // 没有服务

            result.setResCode(CodeConstants.INT_503);

            String resMsg = this.i18nMessage.getStringMessage("zuul.error.service.does.not.have.available");
            String[] errMsgArr = ex.getMessage().split(":");
            if (errMsgArr.length >= 2) {
                resMsg += ":" + errMsgArr[1];
            }
            result.setResMsg(resMsg);

            log.error("uri:{},error:{}", request.getRequestURI(), ex);
        } else { // 其它错误
            if (ex.getMessage() != null) {
                result.setResMsg(ex.getMessage());
            }
            log.error("Error during filtering", ex);
        }
        responseOutJson(ctx.getResponse(), result);
        return null;
    }

    /**
     * 获取异常信息
     *
     * @param e 异常
     * @return 异常信息
     */
    private Throwable getException(Throwable e) {
        e = e.getCause();
        while (e.getCause() != null) {
            e = e.getCause();
        }
        return e;
    }

    /**
     * 转化为json数据输出
     *
     * @param response 响应
     * @param result   数据体
     */
    private void responseOutJson(HttpServletResponse response, ResponseResult result) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            String json = mapper.writeValueAsString(result);
            responseOutJson(response, json);
        } catch (JsonProcessingException e) {
            log.error("转换Json错误：", e);
        }
    }

    /**
     * 转化为json数据输出
     *
     * @param response 响应
     * @param json     数据体
     */
    private static void responseOutJson(HttpServletResponse response, String json) {
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.getOutputStream().write(json.getBytes("UTF-8"));

            response.flushBuffer();

        } catch (IOException e) {
            log.error("response输出错误：", e);
        }
    }

}
