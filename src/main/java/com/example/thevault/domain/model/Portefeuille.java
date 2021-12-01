// Created by carme
// Creation date 30/11/2021

package com.example.thevault.domain.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class Portefeuille {

    int portefeuilleId;
    Map<Cryptomunt, Double> saldiCryptomunten;

    private final Logger logger = LoggerFactory.getLogger(Portefeuille.class);

    public Portefeuille() {
        super();
        logger.info("New Portefeuille");
    }
}
