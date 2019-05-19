package de.esports.aeq.admins.security.web;

import de.esports.aeq.admins.security.domain.RoleTa;
import de.esports.aeq.admins.security.service.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {

    private final ModelMapper mapper;
    private final RoleService service;

    @Autowired
    public RoleController(ModelMapper mapper, RoleService service) {
        this.mapper = mapper;
        this.service = service;
    }

    @GetMapping
    @ResponseBody
    public List<RoleTa> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public RoleTa findOne(@PathVariable Long id) {
        return service.findOne(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody RoleCreateRequestDTO request) {
        RoleTa role = mapper.map(request, RoleTa.class);
        service.create(role);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @Valid @RequestBody RoleUpdateRequestDTO request) {
        RoleTa role = mapper.map(request, RoleTa.class);
        role.setId(id);
        service.update(role);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
