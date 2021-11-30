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

    public Klant(String naam, Adres adres, int BSN, LocalDate geboortedatum) {
        logger.info("New Klant");
        this.naam = naam;
        this.adres = adres;
        this.BSN = BSN;
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
