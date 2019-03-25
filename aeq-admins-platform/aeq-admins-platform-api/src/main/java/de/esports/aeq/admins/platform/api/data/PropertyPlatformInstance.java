package de.esports.aeq.admins.platform.api.data;

import de.esports.aeq.admins.common.SimplePropertyMap;
import de.esports.aeq.admins.platform.api.PlatformInstance;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PropertyPlatformInstance implements SimplePropertyMap, PlatformInstance {

    private Map<String, String> properties = new HashMap<>();

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
