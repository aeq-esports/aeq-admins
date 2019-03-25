package de.esports.aeq.admins.platform.api;

import java.util.Collection;

public interface PlatformInstanceProvider<T extends PlatformInstance> {

    Class<T> getType();

    Collection<T> getPlatformInstances();

    T get(Long instanceId);

    T create(T instance);

    T update(T instance);

}
