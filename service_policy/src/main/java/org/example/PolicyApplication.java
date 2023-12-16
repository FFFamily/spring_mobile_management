package org.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("org.example.mapper")
public class PolicyApplication {
    public static void main(String[] args) {
        System.out.println(11);
        SpringApplication.run(PolicyApplication.class, args);
    }
}
