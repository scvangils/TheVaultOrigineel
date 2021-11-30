// Created by carme
// Creation date 30/11/2021

package com.example.thevault.domain.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Adres {
    private String straatnaam;
    private String toevoeging;
    private int huisnummer;
    private String postcode;
    private String plaatsnaam;

    private final Logger logger = LoggerFactory.getLogger(Adres.class);

    public Adres(){
        super();
        logger.info("Leeg adres, no args constructor");
    }

    public Adres(String straatnaam, int huisnummer, String toevoeging, String postcode, String plaatsnaam) {
        super();
        logger.info("New Adres");
        this.straatnaam = straatnaam;
        this.huisnummer = huisnummer;
        this.toevoeging = toevoeging;
        this.postcode = postcode;
        this.plaatsnaam = plaatsnaam;
    }
}
