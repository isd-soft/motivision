package com.inther;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

@SpringBootApplication(scanBasePackages = {"com.inther","com.inther.repo","com.inther.entity","com.inther.controller"})
@EnableJpaRepositories
public class Application {

     public static void main(String[] args) {
         SpringApplication.run(Application.class, args);
    }
}