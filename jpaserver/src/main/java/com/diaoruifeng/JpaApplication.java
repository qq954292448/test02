package com.diaoruifeng;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class JpaApplication {
    public static void main(String[] args) {
        SpringApplication.run(JpaApplication.class,args);
    }
}
