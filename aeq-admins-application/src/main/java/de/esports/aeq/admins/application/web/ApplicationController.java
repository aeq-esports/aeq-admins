package de.esports.aeq.admins.application.web;

import de.esports.aeq.admins.application.domain.ApplicationTa;
import de.esports.aeq.admins.application.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/applications")
public class ApplicationController {

    private final ApplicationService service;

    @Autowired
    public ApplicationController(ApplicationService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create() {
        ApplicationTa applicationTa = new ApplicationTa();
        applicationTa.setText("Hallo Welt");

        service.create(applicationTa);
    }
}
