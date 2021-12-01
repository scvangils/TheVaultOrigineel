// Created by carme
// Creation date 30/11/2021

package com.example.thevault.domain.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

public class Klant extends Gebruiker {
    private String naam;
    private Adres adres;
    private int BSN;
    private LocalDate geboortedatum;


    private final Logger logger = LoggerFactory.getLogger(Klant.class);

    public Klant(){
        super();
        logger.info("Lege klant, no args constructor");
    }

    public Klant(int gebruikerID, String gebruikersnaam, String wachtwoord,
                 Portefeuille portefeuille, Rekening rekening,
                 String naam, Adres adres, int BSN, LocalDate geboortedatum) {
        super(gebruikerID, gebruikersnaam, wachtwoord);
        this.naam = naam;
        this.adres = adres;
        this.BSN = BSN;
        this.geboortedatum = geboortedatum;
        logger.info("New Klant, all args constructor");
    }
    public Klant(String gebruikersnaam, String wachtwoord,
                 String naam, Adres adres, int BSN, LocalDate geboortedatum){

    }
    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public Adres getAdres() {
        return adres;
    }

    public void setAdres(Adres adres) {
        this.adres = adres;
    }

    public int getBSN() {
        return BSN;
    }

    public void setBSN(int BSN) {
        this.BSN = BSN;
    }

    public LocalDate getGeboortedatum() {
        return geboortedatum;
    }

    public void setGeboortedatum(LocalDate geboortedatum) {
        this.geboortedatum = geboortedatum;
    }

    @Override
    public String toString() {
        return "Klant{" +
                "naam='" + naam + '\'' +
                ", adres=" + adres +
                ", BSN=" + BSN +
                ", geboortedatum=" + geboortedatum +
                '}';
    }
}
