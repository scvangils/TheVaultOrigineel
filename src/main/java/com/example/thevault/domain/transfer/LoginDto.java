package com.example.thevault.domain.transfer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.slf4j.LoggerFactory;

import org.slf4j.Logger;

public class LoginDto {
    private String gebruikersnaam;
    private String wachtwoord;

    @JsonIgnore
    private final Logger logger = LoggerFactory.getLogger(LoginDto.class);

    public LoginDto() {
        super();
        logger.info("New LoginDto");
    }

    public String getGebruikersnaam() {
        return gebruikersnaam;
    }

    public void setGebruikersnaam(String gebruikersnaam) {
        this.gebruikersnaam = gebruikersnaam;
    }

    public String getWachtwoord() {
        return wachtwoord;
    }

    public void setWachtwoord(String wachtwoord) {
        this.wachtwoord = wachtwoord;
    }
}
