// Created by S.C. van Gils
// Creation date 20-12-2021

package com.example.thevault.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Een trigger is een waarde die een actie inzet:
 * In dit geval een maximale koopprijs of een minimale verkoopprijs van een specifieke cryptomunt
 * met een bepaalde gevraagde of aangeboden hoeveelheid
 */

public class Trigger {



    @JsonIgnore
    private final Logger logger = LoggerFactory.getLogger(Trigger.class);

    private Gebruiker gebruiker;
    private Cryptomunt cryptomunt;
    private double triggerPrijs;
    private double aantal;
    private boolean isKoper; // TODO juiste aanpak?
    // Datum nodig?
    // Id nodig?

    public Trigger() {
        super();
        logger.info("New Trigger");
    }
    public Trigger(Gebruiker gebruiker, Cryptomunt cryptomunt, double triggerPrijs, double aantal, boolean isKoper){
        super();
        this.gebruiker = gebruiker;
        this.cryptomunt = cryptomunt;
        this.triggerPrijs = triggerPrijs;
        this.aantal = aantal;
        this.isKoper = isKoper;
        logger.info("New Trigger, all-args constructor");
    }
    public Trigger(double triggerPrijs, double aantal, boolean isKoper) {
        this(null, null, triggerPrijs,aantal, isKoper );
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
