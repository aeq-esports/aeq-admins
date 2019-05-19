package de.esports.aeq.admins.security.web;

import de.esports.aeq.admins.security.domain.PrivilegeTa;
import de.esports.aeq.admins.security.domain.RoleTa;
import de.esports.aeq.admins.security.service.PrivilegeService;
import de.esports.aeq.admins.security.web.types.RoleCreateRequestDTO;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class MapperBean {

    private final ModelMapper mapper;
    private final PrivilegeService privilegeService;

    @Autowired
    public MapperBean(ModelMapper mapper, PrivilegeService privilegeService) {
        this.mapper = mapper;
        this.privilegeService = privilegeService;
    }

    @PostConstruct
    private void registerTypeMaps() {
        mapper.createTypeMap(RoleCreateRequestDTO.class, RoleTa.class)
                .addMappings(map -> map.using(convertRoleNames()).map
                        (RoleCreateRequestDTO::getPrivileges, RoleTa::setPrivileges));
    }

    private Converter<Set<String>, Set<PrivilegeTa>> convertRoleNames() {
        return context ->
                context.getSource().stream()
                        .map(privilegeService::findOneByName)
                        .collect(Collectors.toSet());
    }

}
