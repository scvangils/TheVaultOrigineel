// Created by S.C. van Gils
// Creation date 12-1-2022

package com.example.thevault.domain.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

public class TriggerVerkoper extends Trigger{

    private final Logger logger = LoggerFactory.getLogger(TriggerVerkoper.class);

    public TriggerVerkoper() {
        super();
        logger.info("New TriggerVerkoper");
    }

    public TriggerVerkoper(Gebruiker gebruiker, Cryptomunt cryptomunt, double triggerPrijs, double aantal) {
        super(gebruiker, cryptomunt, triggerPrijs, aantal);
    }

    public TriggerVerkoper(int triggerId, double triggerPrijs, double aantal, LocalDate datum) {
        super(triggerId, triggerPrijs, aantal, datum);
    }

    @Override
    public String toString() {
        return "TriggerVerkoper: " + super.toString();
    }

}
