package de.esports.aeq.admins.members.domain.account;

import java.util.Map;

public interface SimplePropertyMap {

    void addProperty(String key, String property);

    Map<String, String> getProperties();
}
