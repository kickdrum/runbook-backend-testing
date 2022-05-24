package com.runbook.backend.testing;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class RunbookBackendTestingApplication {

    public static void main(String[] args) {
        SpringApplication.run(RunbookBackendTestingApplication.class, args);

    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
