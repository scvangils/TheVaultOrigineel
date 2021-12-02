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

    @Override
    public String toString() {
        return "Adres{" +
                "straatnaam='" + straatnaam + '\'' +
                ", toevoeging='" + toevoeging + '\'' +
                ", huisnummer=" + huisnummer +
                ", postcode='" + postcode + '\'' +
                ", plaatsnaam='" + plaatsnaam + '\'' +
                '}';
    }

    public String getStraatnaam() {
        return straatnaam;
    }

    public void setStraatnaam(String straatnaam) {
        this.straatnaam = straatnaam;
    }

    public String getToevoeging() {
        return toevoeging;
    }

    public void setToevoeging(String toevoeging) {
        this.toevoeging = toevoeging;
    }

    public int getHuisnummer() {
        return huisnummer;
    }

    public void setHuisnummer(int huisnummer) {
        this.huisnummer = huisnummer;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getPlaatsnaam() {
        return plaatsnaam;
    }

    public void setPlaatsnaam(String plaatsnaam) {
        this.plaatsnaam = plaatsnaam;
    }

}
