package com.hurry.ucenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Hurry
 * @CreateTime: 2022/3/6
 * To change this template use File | Settings | File Templates.
 * Description:
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan("com.hurry")
@MapperScan("com.hurry.ucenter.mapper")
public class UcenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(UcenterApplication.class,args);
    }
}
