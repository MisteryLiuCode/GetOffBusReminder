package com.liu.getOffBusReminder;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.liu.getOffBusReminder.dao")
public class GetOffBusReminderApplication {

    public static void main(String[] args) {
        SpringApplication.run(GetOffBusReminderApplication.class, args);
    }

}
