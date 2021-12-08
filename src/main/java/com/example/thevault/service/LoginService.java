// Created by Elise Olthof
// Creation date 12-12-2021

package com.example.thevault.service;

import com.example.thevault.domain.mapping.repository.RootRepository;
import com.example.thevault.domain.model.Klant;
import com.example.thevault.support.authorization.AuthorizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class LoginService {

    private RootRepository rootRepository;
    private AuthorizationService authorizationService;

    private UUID opaakToken;
    private String jwtToken;


    private final Logger logger = LoggerFactory.getLogger(KlantService.class);

    public LoginService(RootRepository rootRepository, AuthorizationService authorizationService) {
        super();
        this.rootRepository = rootRepository;
        this.authorizationService = authorizationService;
        logger.info("New LoginService");
    }

    public String koppelTokenNaValidatieLogin (Klant klant){
        authorizationService.authoriseer(klant);

        // nog goed toevoegen:
        return jwtToken;
    }


}