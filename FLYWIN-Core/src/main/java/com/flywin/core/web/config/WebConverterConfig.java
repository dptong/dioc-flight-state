/**
 * <li>文件名：WebConverterConfig.java
 * <li>说明：
 * <li>创建人： 曾明辉
 * <li>创建日期：2018年11月10日
 * <li>修改人：
 * <li>修改日期：
 */
package com.flywin.core.web.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;
import java.io.File;

/**
 *
 * @Description: TODO
 * @author: 曾明辉
 * @date: 2019年8月14日
 */
@Slf4j
@Configuration
public class WebConverterConfig {

    /**
     *
     * @Title mappingJackson2HttpMessageConverter
     * @Description 解决返回json数据会加载Lazy对象问题
     * @return
     * @return MappingJackson2HttpMessageConverter
     * @author 曾明辉
     * @date: 2019年8月14日
     */
    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = jsonConverter.getObjectMapper();
        objectMapper.registerModule(new Hibernate5Module());

        return jsonConverter;
    }

    /**
     *
     * @Title multipartConfigElement
     * @Description 解决文件上传不能超过1M的问题
     * @return
     * @return MultipartConfigElement
     * @author 曾明辉
     * @date: 2019年10月24日
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        // 最大文件包
        DataSize maxFileSize = DataSize.ofMegabytes(500);
        // 最大请求包
        DataSize maxRequestSize = DataSize.ofMegabytes(500);
        factory.setMaxFileSize(maxFileSize);
        factory.setMaxRequestSize(maxRequestSize);
        String osName = System.getProperty("os.name").toLowerCase();
        log.info("操作系统:" + osName);
        if (osName.contains("linux")) {
            final String tmpPath = "/app/tmp";
            File dirFlie = new File(tmpPath);
            if (!dirFlie.exists()) {
                dirFlie.mkdir();
                log.info("已创建上传文件临时目录【{}】", tmpPath);
            }
            factory.setLocation(tmpPath);
            log.info("设置linux上传文件临时目录成功");
        }
        return factory.createMultipartConfig();
    }
}
