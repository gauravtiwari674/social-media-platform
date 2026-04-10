package com.socialmedia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class SocialMediaPlatformApplication {
    public static void main(String[] args) {
        SpringApplication.run(SocialMediaPlatformApplication.class, args);
    }
}