package com.management.timesheet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(scanBasePackages = {"com.management.timesheet", "com.management.common"})
@EnableDiscoveryClient
public class TimeEntryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TimeEntryServiceApplication.class, args);
    }
}
