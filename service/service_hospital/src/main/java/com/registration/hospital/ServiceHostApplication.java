package com.registration.hospital;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients("com.registration")
@ComponentScan(basePackages = "com.registration")
public class ServiceHostApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceHostApplication.class, args);
    }
}
