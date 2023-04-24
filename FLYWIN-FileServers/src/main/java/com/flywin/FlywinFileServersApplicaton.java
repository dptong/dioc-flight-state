package com.flywin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * <li>类型名称：
 * <li>说明：
 * <li>创建人： 刘二奇
 * <li>创建日期：2019年7月23日
 * <li>修改人：
 * <li>修改日期：
 */
@EnableEurekaClient
@SpringBootApplication
@EnableSwagger2
@EnableFeignClients
@MapperScan("com.flywin.db.mapper")
public class FlywinFileServersApplicaton {

    /**
     * @param args 参数
     * @Title main
     * @Description 主函数
     * @author 刘二奇
     * @date: 2019年8月19日
     */
    public static void main(String[] args) {
        SpringApplication.run(FlywinFileServersApplicaton.class, args);
    }

}
