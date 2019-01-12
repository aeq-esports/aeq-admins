package de.esports.aeq.admins.trials.common;

import de.esports.aeq.admins.security.GrantedAuthorityHolder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum Privileges implements GrantedAuthorityHolder {

    READ_TRIAL_PERIOD("READ_TRIAL_PERIOD"),
    CREATE_TRIAL_PERIOD("CREATE_TRIAL_PERIOD"),
    UPDATE_TRIAL_PERIOD("UPDATE_TRIAL_PERIOD"),
    DELETE_TRIAL_PERIOD("DELETE_TRIAL_PERIOD");

    private final String name;

    Privileges(String name) {
        this.name = name;
    }

    @Override
    public GrantedAuthority toGrantedAuthority() {
        return new SimpleGrantedAuthority(name);
    }
}
