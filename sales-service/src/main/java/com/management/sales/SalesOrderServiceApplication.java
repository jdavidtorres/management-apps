package com.management.sales;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(scanBasePackages = {"com.management.sales", "com.management.common"})
@EnableDiscoveryClient
public class SalesOrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SalesOrderServiceApplication.class, args);
    }
}
