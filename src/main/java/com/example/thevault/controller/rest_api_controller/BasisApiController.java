// Created by carme
// Creation date 01/12/2021

package com.example.thevault.controller.rest_api_controller;

import com.example.thevault.service.Facade;
import com.example.thevault.service.KlantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

@RestController
public abstract class BasisApiController {

    private final Logger logger = LoggerFactory.getLogger(BasisApiController.class);

    protected KlantService klantService;
    protected Facade facade;

    public BasisApiController(Facade facade, KlantService klantService) {
        super();
        this.facade = facade;
        this.klantService = klantService;
        logger.info("New BasisApiController");
    }

}
