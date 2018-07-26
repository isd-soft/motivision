package com.inther;


import com.inther.service.ExceptionalService;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

@SpringBootApplication(scanBasePackages =
               {"com.inther",
                "com.inther.repo",
                "com.inther.entity",
                "com.inther.controller",
                "com.inther.aspect",
               "com.inther.service"})
@Configuration
@EnableAspectJAutoProxy
@EnableJpaRepositories
@EnableAutoConfiguration
public class Application extends SpringBootServletInitializer {

    @Autowired
    ExceptionalService service;

    public void run(String... args) throws Exception {
        try {
            service.throwException();
        } catch (Exception ex) {

        }
    }

     public static void main(String[] args) {
         SpringApplication.run(Application.class, args);
    }
}