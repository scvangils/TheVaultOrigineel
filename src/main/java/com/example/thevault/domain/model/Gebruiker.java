// Created by carme
// Creation date 30/11/2021

package com.example.thevault.domain.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

abstract class Gebruiker {
    private int gebruikerID;
    private String gebruikersnaam;
    private String wachtwoord;
    private Portefeuille portefeuille;
    private Rekening rekening;

    private final Logger logger = LoggerFactory.getLogger(Gebruiker.class);

    public Gebruiker() {
        super();
        logger.info("Lege Gebruiker, no args constructor");
    }

    public Gebruiker(Portefeuille portefeuille, Rekening rekening){
        super();
        this.portefeuille = portefeuille;
        this.rekening = rekening;
        logger.info("Lege gebruiker met rekening en portefeuille");
    }

    public Gebruiker(int gebruikerID, String gebruikersnaam, String wachtwoord){
        this(new Portefeuille(), new Rekening());
        this.gebruikerID = gebruikerID;
        this.gebruikersnaam = gebruikersnaam;
        this.wachtwoord = wachtwoord;
        logger.info("Gebruiker" + this + "aangemaakt");
    }






}
