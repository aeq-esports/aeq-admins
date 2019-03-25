package de.esports.aeq.admins.platform.api;

import java.util.Collection;

public interface PlatformInstanceService {

    Collection<PlatformInstance>
    getPlatformInstances(Class<? extends PlatformInstance> instanceClass);
}
