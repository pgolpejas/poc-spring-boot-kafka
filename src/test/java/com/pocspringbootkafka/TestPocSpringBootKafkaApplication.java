package com.pocspringbootkafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;

@TestConfiguration(proxyBeanMethods = false)
class TestPocSpringBootKafkaApplication {

    @Bean
    @ServiceConnection
    PostgreSQLContainer<?> postgresContainer() {
        return new PostgreSQLContainer<>("postgres:latest");
    }

    public static void main(String[] args) {
        SpringApplication.from(PocSpringBootKafkaApplication::main).with(TestPocSpringBootKafkaApplication.class).run(args);
    }

}
