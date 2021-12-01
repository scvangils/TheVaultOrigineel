// Created by carme
// Creation date 30/11/2021

package com.example.thevault.domain.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Cryptomunt {

    private int cryptomuntId;
    private String naam;
    private String afkorting;
    private double waarde;

    private final Logger logger = LoggerFactory.getLogger(Cryptomunt.class);

    public Cryptomunt() {
        super();
        logger.info("New Cryptomunt, no args constructor");
    }


    public Cryptomunt (int cryptomuntId, String naam, String afkorting, double waarde){
        this.cryptomuntId = cryptomuntId;
        this.naam = naam;
        this.afkorting = afkorting;
        this.waarde = waarde;
        logger.info("Cryptomunt:" + this);
    }

    @Override
    public String toString() {
        return "Cryptomunt{" +
                "cryptomuntId=" + cryptomuntId +
                ", naam='" + naam + '\'' +
                ", afkorting='" + afkorting + '\'' +
                ", waarde=" + waarde +
                '}';
    }

    public int getCryptomuntId() {
        return cryptomuntId;
    }

    public void setCryptomuntId(int cryptomuntId) {
        this.cryptomuntId = cryptomuntId;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getAfkorting() {
        return afkorting;
    }

    public void setAfkorting(String afkorting) {
        this.afkorting = afkorting;
    }

    public double getWaarde() {
        return waarde;
    }

    public void setWaarde(double waarde) {
        this.waarde = waarde;
    }

    public Logger getLogger() {
        return logger;
    }
}
