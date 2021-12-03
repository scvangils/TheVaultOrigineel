// Created by carme
// Creation date 02/12/2021

package com.example.thevault.domain.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

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



}
