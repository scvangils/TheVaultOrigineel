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

    @Override
    public String toString() {
        return "Gebruiker{" +
                "gebruikerID=" + gebruikerID +
                ", gebruikersnaam='" + gebruikersnaam + '\'' +
                ", portefeuille=" + portefeuille +
                ", rekening=" + rekening +
                '}';
    }

    public int getGebruikerID() {
        return gebruikerID;
    }

    public void setGebruikerID(int gebruikerID) {
        this.gebruikerID = gebruikerID;
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

    public Portefeuille getPortefeuille() {
        return portefeuille;
    }

    public void setPortefeuille(Portefeuille portefeuille) {
        this.portefeuille = portefeuille;
    }

    public Rekening getRekening() {
        return rekening;
    }

    public void setRekening(Rekening rekening) {
        this.rekening = rekening;
    }


}
