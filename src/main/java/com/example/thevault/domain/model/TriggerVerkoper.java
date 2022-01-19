// Created by S.C. van Gils
// Creation date 12-1-2022

package com.example.thevault.domain.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

public class TriggerVerkoper extends Trigger{

    private final Logger logger = LoggerFactory.getLogger(TriggerVerkoper.class);

    //TODO JavaDoc
    public TriggerVerkoper() {
        super();
        logger.info("New TriggerVerkoper");
    }

    //TODO JavaDoc
    public TriggerVerkoper(Gebruiker gebruiker, Cryptomunt cryptomunt, double triggerPrijs, double aantal) {
        super(gebruiker, cryptomunt, triggerPrijs, aantal);
        logger.info("New TriggerVerkoperVeelGebruikt");
    }

    //TODO JavaDoc
    public TriggerVerkoper(int triggerId, double triggerPrijs, double aantal, LocalDate datum) {
        super(triggerId, triggerPrijs, aantal, datum);
        logger.info("New TriggerVerkoperRowMapper");
    }

    @Override
    public String toString() {
        return "TriggerVerkoper: " + super.toString();
    }
}
