// Created by S.C. van Gils
// Creation date 20-12-2021

package com.example.thevault.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Trigger {

    @JsonIgnore
    private final Logger logger = LoggerFactory.getLogger(Trigger.class);

    private Gebruiker gebruiker;
    private double triggerPrijs;

    public Trigger() {
        super();
        logger.info("New Trigger");
    }
    public Trigger(Gebruiker gebruiker, double triggerPrijs){
        super();
        this.gebruiker = gebruiker;
        this.triggerPrijs = triggerPrijs;
        logger.info("New Trigger, all-args constructor");
    }
    public Trigger(double triggerPrijs) {
        this(null, triggerPrijs);
        logger.info("New Trigger, RowMapper constructor");
    }

    public Gebruiker getGebruiker() {
        return gebruiker;
    }

    public void setGebruiker(Gebruiker gebruiker) {
        this.gebruiker = gebruiker;
    }

    public double getTriggerPrijs() {
        return triggerPrijs;
    }

    public void setTriggerPrijs(double triggerPrijs) {
        this.triggerPrijs = triggerPrijs;
    }
}
