package com.polo.apollo;

import com.polo.apollo.entity.dto.UserDto;
import com.polo.apollo.entity.modal.system.SysConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author baoqianyong
 */
@MapperScan({"com.polo.apollo.dao"})
@SpringBootApplication
@EnableScheduling
@EnableAsync
@EnableCaching
public class Application {

    public static volatile SysConfig sys;

    public static volatile UserDto user;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
