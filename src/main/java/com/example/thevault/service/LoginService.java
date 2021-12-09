// Created by Elise Olthof
// Creation date 12-12-2021

package com.example.thevault.service;

import com.example.thevault.domain.mapping.repository.RootRepository;
import com.example.thevault.domain.model.Klant;
import com.example.thevault.domain.transfer.LoginDto;
import com.example.thevault.support.authorization.AuthorizationService;
import com.example.thevault.support.hashing.BCryptWachtwoordHash;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;
import java.util.Objects;
import java.util.UUID;

@Service
public class LoginService {

    private RootRepository rootRepository;
    private AuthorizationService authorizationService;

    private UUID opaakToken;
    private String jwtToken;

    private final Logger logger = LoggerFactory.getLogger(LoginService.class);

    @Autowired
    public LoginService(RootRepository rootRepository, AuthorizationService authorizationService) {
        super();
        this.rootRepository = rootRepository;
        this.authorizationService = authorizationService;
        logger.info("New LoginService......");
    }

    public String koppelTokenNaValidatieLogin (Klant klant){
        authorizationService.authoriseer(klant);

        // nog goed toevoegen:
        return jwtToken;
    }

    /**
     * Wim 20211207
     * @return Klant als combinatie gebruikersnaam en wachtwoord correct is, anders geef foutmelding
     */

    public Klant valideerLogin (LoginDto loginDto){
        //vraag wachtwoord op via RootRepos
        if(vindKlantByGebruikersnaam(loginDto.getGebruikersnaam()) == null){
           return null;
        }
        if(!BCryptWachtwoordHash.verifyHash(loginDto.getWachtwoord(), vindKlantByGebruikersnaam(loginDto.getGebruikersnaam()).getWachtwoord())){
            return null;
        }
        return vindKlantByGebruikersnaam(loginDto.getGebruikersnaam());
    }

    public Klant vindKlantByGebruikersnaam(String username){
        return rootRepository.vindKlantByGebruikersnaam(username);
    }


}