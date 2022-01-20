// Created by S.C. van Gils
// Creation date 12-1-2022

package com.example.thevault.domain.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.Objects;

public class TriggerKoper extends Trigger{

    private final Logger logger = LoggerFactory.getLogger(TriggerKoper.class);

    //TODO JavaDoc
    public TriggerKoper() {
        super();
        logger.info("New TriggerKoper");
    }

    //TODO JavaDoc
    public TriggerKoper(Gebruiker gebruiker, Cryptomunt cryptomunt, double triggerPrijs, double aantal) {
        super(gebruiker, cryptomunt, triggerPrijs, aantal);
        logger.info("New TriggerKoperVeelGebruikt");
    }

    //TODO JavaDoc
    public TriggerKoper(int triggerId, double triggerPrijs, double aantal, LocalDate datum) {
        super(triggerId, triggerPrijs, aantal, datum);
        logger.info("New TriggerKoperRowMapper");
    }

    @Override
    public String toString() {
        return "TriggerKoper:" + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TriggerKoper)) return false;
        return super.equals(o);
    }
}
