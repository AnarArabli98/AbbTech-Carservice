package com.abbtech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class CarServiceApplication {

    static void main(String[] args) {
        SpringApplication.run(CarServiceApplication.class, args);
    }


}
