// Created by carme
// Creation date 17/01/2022

package com.example.thevault.domain.transfer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactieStartDto that = (TransactieStartDto) o;
        return cryptomuntId == that.cryptomuntId && gebruikersNaam.equals(that.gebruikersNaam);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gebruikersNaam, cryptomuntId);
    }
}
