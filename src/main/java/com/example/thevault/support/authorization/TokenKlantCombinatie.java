/*
 * @Author Elise Olthof
 * 12-07-2021
 * */
package com.example.thevault.support.authorization;

import com.example.thevault.domain.model.Klant;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;


public class TokenKlantCombinatie {

    private UUID key;
    private Klant klant;

    @JsonIgnore
    private final Logger logger = LoggerFactory.getLogger(AuthorizationSupport.class);

    public TokenKlantCombinatie(UUID key, Klant klant) {
        super();
        this.key = key;
        this.klant = klant;
        logger.info("New TokenKlantCombinatie");
    }


}
