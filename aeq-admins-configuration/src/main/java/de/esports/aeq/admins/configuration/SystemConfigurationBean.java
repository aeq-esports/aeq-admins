package de.esports.aeq.admins.configuration;

import org.springframework.stereotype.Component;

@Component
public class SystemConfigurationBean implements SystemConfiguration {

    @Override
    public PasswordStrength getPasswordStrength() {
        return new PasswordStrength(8, true, true, true, true);
    }
}
