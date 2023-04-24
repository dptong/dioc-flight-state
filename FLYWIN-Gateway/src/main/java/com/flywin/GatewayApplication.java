package com.flywin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@EnableEurekaClient
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class,
        JpaRepositoriesAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class GatewayApplication {

    /**
     * 启动器
     *
     * @param args args
     */
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

}
