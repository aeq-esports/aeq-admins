package de.esports.aeq.admins.members.domain.account;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PropertyPlatformData implements SimplePropertyMap {

    private Map<String, String> properties = new HashMap<>();

    public PropertyPlatformData() {

    }

    public PropertyPlatformData(Map<String, String> properties) {
        this.properties = properties;
    }

    @Override
    public void addProperty(String key, String property) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(property);
        properties.put(key, property);
    }

    @Override
    public Map<String, String> getProperties() {
        return Collections.unmodifiableMap(properties);
    }
}
