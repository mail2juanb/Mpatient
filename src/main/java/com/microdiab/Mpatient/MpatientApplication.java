package com.microdiab.Mpatient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;


@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class MpatientApplication {

    public static void main(String[] args) {
        SpringApplication.run(MpatientApplication.class, args);
    }

}
