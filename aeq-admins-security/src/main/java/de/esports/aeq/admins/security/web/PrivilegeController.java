package de.esports.aeq.admins.security.web;

import de.esports.aeq.admins.security.domain.PrivilegeTa;
import de.esports.aeq.admins.security.service.PrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/privileges")
public class PrivilegeController {

    private PrivilegeService service;

    @Autowired
    public PrivilegeController(PrivilegeService service) {
        this.service = service;
    }

    @GetMapping
    @ResponseBody
    @Secured("READ_PRIVILEGES")
    public List<PrivilegeTa> findAll() {
        return service.findAll();
    }
}
