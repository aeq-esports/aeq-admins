package de.esports.aeq.admins.platform.impl;

import de.esports.aeq.admins.platform.api.PlatformInstance;
import de.esports.aeq.admins.platform.api.PlatformInstanceProvider;
import de.esports.aeq.admins.platform.api.PlatformInstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class PlatformInstanceServiceBean implements PlatformInstanceService {

    private final ApplicationContext context;

    /**
     * Registered providers through integrations.
     */
    private Map<Class<?>, PlatformInstanceProvider<?>> providers = new HashMap<>();

    @Autowired
    public PlatformInstanceServiceBean(ApplicationContext context) {
        this.context = context;
    }

    @PostConstruct
    private void registerProviders() {
        context.getBeansOfType(PlatformInstanceProvider.class).values()
                .forEach(provider -> providers.put(provider.getType(), provider));
    }

    @Override
    public Collection<PlatformInstance>
    getPlatformInstances(Class<? extends PlatformInstance> instanceClass) {
        PlatformInstanceProvider<?> provider = providers.get(instanceClass);
        if (provider == null) {
            throw new IllegalArgumentException("No provider found for " + instanceClass);
        }

        @SuppressWarnings("unchecked")
        Collection<PlatformInstance> instances =
                (Collection<PlatformInstance>) provider.getPlatformInstances();

        return instances;
    }
}
