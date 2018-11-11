package de.esports.aeq.admins.core;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.concurrent.Executor;

@SpringBootApplication
@ComponentScan(basePackages = "de.esports.aeq.admins")
@EnableJpaRepositories(basePackages = "de.esports.aeq.admins")
@EntityScan(basePackages = "de.esports.aeq.admins")
@PropertySource(value = "application.properties")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
