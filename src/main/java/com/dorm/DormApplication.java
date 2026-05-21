package com.dorm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 宿舍管理系统启动类。
 * 采用分层架构：controller -> service -> repository，与 integration 层松耦合。
 */
@SpringBootApplication
public class DormApplication {

    public static void main(String[] args) {
        SpringApplication.run(DormApplication.class, args);
    }
}
