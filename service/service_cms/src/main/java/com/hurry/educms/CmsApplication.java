package com.hurry.educms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Hurry
 * @CreateTime: 2022/3/5
 * To change this template use File | Settings | File Templates.
 * Description:
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.hurry"}) //指定扫描位置
@EnableDiscoveryClient  //nacos注册
@MapperScan("com.hurry.educms.mapper")
public class CmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(CmsApplication.class, args);
    }
}
