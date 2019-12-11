package com.easyim;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 启动程序
 * @author imessage
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class EMessageApplication
{
    public static void main(String[] args) {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(EMessageApplication.class, args);
    }
}