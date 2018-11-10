package de.esports.aeq.admins.trials.service;

import de.esports.aeq.admins.trials.domain.TrialPeriodConfigTa;
import de.esports.aeq.admins.trials.jpa.TrialPeriodConfigRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class DefaultTrialPeriodConfigProvider {

    private static final Logger LOG = LoggerFactory.getLogger(
            DefaultTrialPeriodConfigProvider.class);

    private TrialPeriodConfigRepository repository;

    @Autowired
    public DefaultTrialPeriodConfigProvider(TrialPeriodConfigRepository repository) {
        this.repository = repository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void setupDefaultTrialPeriodConfig() {
        LOG.debug("Checking for default trial period configuration.");

        var firstConfig = repository.findAll().stream().findFirst();

        if (firstConfig.isPresent()) {
            LOG.info("Found default config for trial periods: {}", firstConfig);
        } else {
            TrialPeriodConfigTa config = createDefault();
            LOG.info("No default configuration for trial periods found, saving default one with " +
                    "{}", config);
            repository.save(config);
        }
    }

    private TrialPeriodConfigTa createDefault() {
        TrialPeriodConfigTa config = new TrialPeriodConfigTa();
        config.setTrialPeriod(Duration.ofSeconds(60));
        return config;
    }
}
