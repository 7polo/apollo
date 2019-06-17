package com.polo.apollo;

import com.polo.apollo.entity.modal.system.SysConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author baoqianyong
 */
@MapperScan({"com.polo.apollo.dao"})
@SpringBootApplication
@EnableScheduling
public class Application {

    public static SysConfig sys;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
