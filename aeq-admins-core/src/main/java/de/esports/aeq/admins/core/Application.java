package de.esports.aeq.admins.core;

import de.esports.aeq.admins.common.YamlPropertySourceFactory;
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "de.esports.aeq.admins")
@EnableJpaRepositories(basePackages = "de.esports.aeq.admins")
@EnableJpaAuditing
@EntityScan(basePackages = "de.esports.aeq.admins")
@PropertySource("default.config.properties")
@PropertySource("logging.properties")
@EnableProcessApplication
@PropertySource(factory = YamlPropertySourceFactory.class, value = "classpath:application.yml")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
