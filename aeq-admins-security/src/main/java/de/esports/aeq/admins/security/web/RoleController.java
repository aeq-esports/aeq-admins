package de.esports.aeq.admins.security.web;

import de.esports.aeq.admins.security.domain.RoleTa;
import de.esports.aeq.admins.security.service.PrivilegeService;
import de.esports.aeq.admins.security.service.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/roles")
public class RoleController {

    private final ModelMapper mapper;
    private final RoleService service;
    private final PrivilegeService privilegeService;

    static {

    }

    @Autowired
    public RoleController(ModelMapper mapper, RoleService service,
            PrivilegeService privilegeService) {
        this.mapper = mapper;
        this.service = service;
        this.privilegeService = privilegeService;
    }

    @PostConstruct
    private void setupMapper() {
        mapper.createTypeMap(RoleCreateRequestDTO.class, RoleTa.class)
                .addMappings(map -> map.using(context -> ((Set<String>) context.getSource())
                        .stream()
                        .map(privilegeService::findOneByName)
                        .collect(Collectors.toSet()))
                        .map(RoleCreateRequestDTO::getPrivileges, RoleTa::setPrivileges));
    }

    @GetMapping
    @ResponseBody
    public List<RoleTa> findAll() {
        return service.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody RoleCreateRequestDTO request) {
        RoleTa role = mapper.map(request, RoleTa.class);
        service.create(role);
    }
}
