// Created by S.C. van Gils
// Creation date 5-1-2022

/**
 * Deze klasse zorgt ervoor dat de voor een klant noodzakelijke informatie van zijn eerdere transacties naar de front-end kan
 */

package com.example.thevault.domain.transfer;

import com.example.thevault.domain.model.Cryptomunt;
import com.example.thevault.domain.model.Gebruiker;
import com.example.thevault.domain.model.Transactie;
import com.example.thevault.domain.model.Trigger;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;

public class HistorieDto {

    @JsonIgnore
    private final Logger logger = LoggerFactory.getLogger(HistorieDto.class);

    private List<Transactie> transactieLijst;
    private List<Trigger> koopTriggers;
    private List<Trigger> verkoopTriggers;

    public HistorieDto() {
        super();
        logger.info("New HistorieDto");
    }
    public HistorieDto(Gebruiker klant){
        this.transactieLijst = klant.getTransacties();
    }

    public List<Transactie> getTransactieLijst() {
        return transactieLijst;
    }

    public void setTransactieLijst(List<Transactie> transactieLijst) {
        this.transactieLijst = transactieLijst;
    }

    public List<Trigger> getKoopTriggers() {

        return koopTriggers;
    }

    public void setKoopTriggers(List<Trigger> koopTriggers) {
        this.koopTriggers = koopTriggers;
    }

    public List<Trigger> getVerkoopTriggers() {
        return verkoopTriggers;
    }

    public void setVerkoopTriggers(List<Trigger> verkoopTriggers) {
        this.verkoopTriggers = verkoopTriggers;
    }
}
