package de.esports.aeq.admins.integration.riot;

import de.esports.aeq.admins.platform.api.data.PropertyPlatformData;

import static java.util.Objects.requireNonNull;

public final class RiotPlatformData extends PropertyPlatformData {

    public static final String REGION = "REGION";

    public RiotPlatformData(String region) {
        addProperty(REGION, requireNonNull(region));
    }
}
