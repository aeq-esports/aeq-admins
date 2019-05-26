package de.esports.aeq.admins.security.web;

import de.esports.aeq.admins.security.api.User;
import de.esports.aeq.admins.security.api.service.SecurityService;
import de.esports.aeq.admins.security.web.types.UserCreateRequestDTO;
import de.esports.aeq.admins.security.web.types.UserResponseDTO;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final ModelMapper mapper;
    private final SecurityService securityService;

    @Autowired
    public UserController(ModelMapper mapper, SecurityService securityService) {
        this.mapper = mapper;
        this.securityService = securityService;
    }

    @GetMapping
    @ResponseBody
    public List<UserResponseDTO> findAll() {
        return securityService.getAll().stream()
                .map(user -> mapper.map(user, UserResponseDTO.class))
                .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void register(@Valid @RequestBody UserCreateRequestDTO request) {
        User user = mapper.map(request, User.class);
        securityService.create(user);
    }
}
