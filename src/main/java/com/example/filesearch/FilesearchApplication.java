package com.example.filesearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class FilesearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(FilesearchApplication.class, args);
    }

}

