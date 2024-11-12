package com.itrihua.health_api_demo;


import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.itrihua.health_api_demo.mapper")
public class HealthApiDemoApplication {
	private static final Logger logger = LoggerFactory.getLogger(HealthApiDemoApplication.class);

    public static void main(String[] args) {
		SpringApplication.run(HealthApiDemoApplication.class, args);
	}

}
