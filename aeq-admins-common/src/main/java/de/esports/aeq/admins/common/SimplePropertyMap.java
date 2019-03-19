package de.esports.aeq.admins.common;

import java.util.Map;

public interface SimplePropertyMap {

    void addProperty(String key, String property);

    Map<String, String> getProperties();
}
