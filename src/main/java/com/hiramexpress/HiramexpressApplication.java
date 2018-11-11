package com.hiramexpress;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.hiramexpress.dao")
@EnableScheduling
public class HiramexpressApplication {

    public static void main(String[] args) {
        SpringApplication.run(HiramexpressApplication.class, args);
    }
}
