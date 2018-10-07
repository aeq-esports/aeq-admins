package de.esports.aeq.admins.security.web;

import de.esports.aeq.admins.security.domain.UserTa;
import de.esports.aeq.admins.security.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    private final ModelMapper mapper;
    private final UserService service;

    @Autowired
    public UserController(ModelMapper mapper, UserService service) {
        this.mapper = mapper;
        this.service = service;
    }

    @GetMapping
    @ResponseBody
    public List<UserResponseDTO> findAll() {
        return service.findAll().stream()
                .map(user -> mapper.map(user, UserResponseDTO.class))
                .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void register(@Valid @RequestBody UserCreateRequestDTO request) {
        UserTa user = mapper.map(request, UserTa.class);
        service.create(user);
    }
}
