/**
 * <li>文件名：CustomDateDeserialize.java
 * <li>说明：
 * <li>创建人： 曾明辉
 * <li>创建日期：2018年11月10日
 * <li>修改人：
 * <li>修改日期：
 */
package com.flywin.core.web.jsonserial;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description: TODO
 * @author: 曾明辉
 * @date: 2019年8月14日
 */
public class DateDeserializer extends JsonDeserializer<Date> {
    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(DateDeserializer.class);

    /**
     * @param jp
     * @param ctxt
     * @return
     * @throws IOException
     * @throws JsonProcessingException
     * @Title deserialize
     * @Description 反序列号
     * @author 曾明辉
     * @date: 2019年8月14日
     */
    @Override
    public Date deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        if (StringUtils.isEmpty(jp.getText())) {
            return null;
        }

        String strDate = jp.getText().trim();
        Date dtDate;

        try {
            SimpleDateFormat formatter;
            if (strDate.contains("T") && strDate.endsWith("Z")) {
                formatter = new SimpleDateFormat(Constants.DATE_TIME_UTC_FORMAT);
                dtDate = formatter.parse(strDate.replace("Z", " UTC"));
                return dtDate;
            } else if (strDate.contains("-")) {
                if (strDate.contains(":")) {
                    formatter = new SimpleDateFormat(Constants.DATE_TIME_HORIZONTAL_FORMAT);
                } else {
                    formatter = new SimpleDateFormat(Constants.DATE_HORIZONTAL_FORMAT);
                }
                dtDate = formatter.parse(strDate);
                return dtDate;
            } else if (strDate.contains("/")) {
                if (strDate.contains(":")) {
                    formatter = new SimpleDateFormat(Constants.DATE_TIME_OBLIQUE_FORMAT);
                } else {
                    formatter = new SimpleDateFormat(Constants.DATE_OBLIQUE_FORMAT);
                }
                dtDate = formatter.parse(strDate);
                return dtDate;
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(String.format("parser %s to Date fail", strDate));
        }

        throw new RuntimeException(String.format("parser %s to Date fail", strDate));
    }

}
