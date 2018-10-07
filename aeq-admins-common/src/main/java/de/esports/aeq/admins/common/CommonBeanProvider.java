package de.esports.aeq.admins.common;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class CommonBeanProvider {

    @Bean
    public ModelMapper modelMapper() {
        return getPrototypedMapper();
    }

    public static ModelMapper getPrototypedMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setSkipNullEnabled(true);
        return mapper;
    }
}
