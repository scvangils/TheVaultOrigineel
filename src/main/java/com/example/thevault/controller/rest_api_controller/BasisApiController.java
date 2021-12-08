// Created by carme
// Creation date 01/12/2021

package com.example.thevault.controller.rest_api_controller;

import com.example.thevault.service.RegistrationService;
import com.example.thevault.service.KlantService;
import com.example.thevault.support.authorization.AuthorizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

@RestController
public abstract class BasisApiController {

    private final Logger logger = LoggerFactory.getLogger(BasisApiController.class);

    protected AuthorizationService authorizationService;
    protected KlantService klantService;
    protected RegistrationService registrationService;

    public BasisApiController(RegistrationService registrationService, KlantService klantService, AuthorizationService authorizationService) {
        super();
        this.registrationService = registrationService;
        this.klantService = klantService;
        this.authorizationService = authorizationService;
        logger.info("New BasisApiController");
    }

}
