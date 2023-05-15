package com.example.es_springboot;

import com.example.es_springboot.repository.es.UserSearchRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(excludeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = UserSearchRepository.class))
@SpringBootApplication
public class EsSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(EsSpringBootApplication.class, args);
    }

}
