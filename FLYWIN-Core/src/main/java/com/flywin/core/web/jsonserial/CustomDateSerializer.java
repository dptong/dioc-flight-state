/**
 * <li>文件名：CustomDateSerializer.java
 * <li>说明：
 * <li>创建人： 曾明辉
 * <li>创建日期：2018年11月10日
 * <li>修改人：
 * <li>修改日期：
 */
package com.flywin.core.web.jsonserial;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @Description: TODO
 * @author: 曾明辉
 * @date: 2019年8月14日
 */
public class CustomDateSerializer extends JsonSerializer<Date> {

    /**
     * 日期格式化
     */
    private final SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_HORIZONTAL_FORMAT);

    /**
     *
     * @Title serialize
     * @Description 序列化
     * @param date
     * @param gen
     * @param serializers
     * @throws IOException
     * @author 曾明辉
     * @date: 2019年8月14日
     */
    @Override
    public void serialize(Date date, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        String strDate = "";
        if (date != null) {
            strDate = sdf.format(date);
        }
        gen.writeString(strDate);

    }

}
