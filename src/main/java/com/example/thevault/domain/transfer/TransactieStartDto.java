// Created by carme
// Creation date 17/01/2022

package com.example.thevault.domain.transfer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * Author: Carmen
 * Beschrijving: er wordt een object aangemaakt met gegevens benodigd voor het opvragen van de gegevens nodig voor
 * het vullen van de transactiepagina
 */

public class TransactieStartDto {

    @JsonIgnore
    private final Logger logger = LoggerFactory.getLogger(TransactieStartDto.class);

    private String gebruikersNaam;
    private int cryptomuntId;

    /**
     * All-args constructor voor transactieStartDto
     * @param gebruikersNaam
     * @param cryptomuntId
     */
    public TransactieStartDto(String gebruikersNaam, int cryptomuntId) {
        super();
        this.gebruikersNaam = gebruikersNaam;
        this.cryptomuntId = cryptomuntId;
        logger.info("New all-args TransactieStartDto");
    }

    public String getGebruikersNaam() {
        return gebruikersNaam;
    }

    public int getCryptomuntId() {
        return cryptomuntId;
    }

    public void setGebruikersNaam(String gebruikersNaam) {
        this.gebruikersNaam = gebruikersNaam;
    }

    public void setCryptomuntId(int cryptomuntId) {
        this.cryptomuntId = cryptomuntId;
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
