// Created by carme
// Creation date 30/11/2021

package com.example.thevault.domain.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Bank extends Gebruiker{

    private final Logger logger = LoggerFactory.getLogger(Bank.class);

    public Bank() {
        super();
        logger.info("New Bank");
    }


}
