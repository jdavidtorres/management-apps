package com.management.projectmgmt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(scanBasePackages = {"com.management.projectmgmt", "com.management.common"})
@EnableDiscoveryClient
public class ProjectServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectServiceApplication.class, args);
    }
}
