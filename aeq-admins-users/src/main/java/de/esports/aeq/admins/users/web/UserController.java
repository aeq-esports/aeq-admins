package de.esports.aeq.admins.users.web;

import de.esports.aeq.admins.common.BooleanExpressionBuilder;
import de.esports.aeq.admins.users.domain.UserTa;
import de.esports.aeq.admins.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<UserTa> findAll(
            @RequestParam(value = "ts3UId", required = false) String ts3UId,
            @RequestParam(value = "firstName", required = false) String firstName) {
        return new BooleanExpressionBuilder<List<UserTa>>()
                .and(ts3UId, Predicates.ts3UId(ts3UId))
                .and(firstName, Predicates.firstName(firstName))
                .mapOrElse(expression -> service.findAll(expression), service::findAll);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public UserTa findOne(@PathVariable("id") Long id) {
        return service.findOneById(id);
    }
}
