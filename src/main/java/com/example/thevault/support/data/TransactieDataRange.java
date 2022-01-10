// Created by S.C. van Gils
// Creation date 8-1-2022

package com.example.thevault.support.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransactieDataRange {

    private final Logger logger = LoggerFactory.getLogger(TransactieDataRange.class);

    private int aantalGebruikers;
    private int maand;

    public TransactieDataRange() {
        super();
        logger.info("New TransactieDataRange");
    }

    public TransactieDataRange(int aantalGebruikers, int maand) {
        this.aantalGebruikers = aantalGebruikers;
        this.maand = maand;
    }

    public int getAantalGebruikers() {
        return aantalGebruikers;
    }

    public int getMaand() {
        return maand;
    }
}
