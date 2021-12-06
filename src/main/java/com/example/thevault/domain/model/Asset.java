// Created by carme
// Creation date 02/12/2021

package com.example.thevault.domain.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

/**
 * @Author: Carmen Rietdijk
 * Beschrijving: De model voor Asset, bestaande uit cryptomunt en aantal, wat de bouwstenen zijn voor de portefeuille
 * van cryptomunten van de klant
 */

public class Asset {

    private Cryptomunt cryptomunt;
    private double aantal;

    private final Logger logger = LoggerFactory.getLogger(Asset.class);

    public Asset() {
        super();
        logger.info("New Asset, no args constructor");
    }

    public Asset(Cryptomunt cryptomunt, double aantal){
        this.cryptomunt = cryptomunt;
        this.aantal = aantal;
        logger.info("Asset: " + this);
    }

    public Cryptomunt getCryptomunt() {
        return cryptomunt;
    }

    public void setCryptomunt(Cryptomunt cryptomunt) {
        this.cryptomunt = cryptomunt;
    }

    public double getAantal() {
        return aantal;
    }

    public void setAantal(double aantal) {
        this.aantal = aantal;
    }
}
