// Created by carme
// Creation date 30/11/2021

package com.example.thevault.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

abstract class Gebruiker {
    private int gebruikerId;
    private String gebruikersnaam;
    private String wachtwoord;
    private static int DEFAULT_GEBRUIKERID = 0;
    @JsonIgnore
    private final Logger logger = LoggerFactory.getLogger(Gebruiker.class);

    public Gebruiker() {
        super();
        logger.info("Lege Gebruiker, no args constructor");
    }


    public Gebruiker(int gebruikerId, String gebruikersnaam, String wachtwoord){
        this.gebruikerId = DEFAULT_GEBRUIKERID;
        this.gebruikersnaam = gebruikersnaam;
        this.wachtwoord = wachtwoord;
        logger.info("Gebruiker " + this + "aangemaakt");
    }

    @Override
    public String toString() {
        return "Gebruiker{" +
                "gebruikerId=" + gebruikerId +
                ", gebruikersnaam='" + gebruikersnaam + '\'' +
                '}';
    }

    public int getGebruikerId() {
        return gebruikerId;
    }

    public void setGebruikerId(int gebruikerId) {
        this.gebruikerId = gebruikerId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Gebruiker)) return false;
        Gebruiker gebruiker = (Gebruiker) o;
        return gebruikerId == gebruiker.gebruikerId && gebruikersnaam.equals(gebruiker.gebruikersnaam) && wachtwoord.equals(gebruiker.wachtwoord);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gebruikerId, gebruikersnaam, wachtwoord);
    }
}
