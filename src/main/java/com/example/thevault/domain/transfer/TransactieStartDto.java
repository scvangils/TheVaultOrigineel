// Created by carme
// Creation date 17/01/2022

package com.example.thevault.domain.transfer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransactieStartDto {

    @JsonIgnore
    private final Logger logger = LoggerFactory.getLogger(TransactieStartDto.class);

    private String gebruikersNaam;
    private int cryptomuntId;

    public TransactieStartDto(String gebruikersNaam, int cryptomuntId) {
        super();
        this.gebruikersNaam = gebruikersNaam;
        this.cryptomuntId = cryptomuntId;
        logger.info("New TransactieStartDto");
    }

    public String getGebruikersNaam() {
        return gebruikersNaam;
    }

    public int getCryptomuntId() {
        return cryptomuntId;
    }
}
