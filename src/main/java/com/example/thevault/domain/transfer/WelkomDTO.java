// Created by E.S. Olthof
// 20211012

package com.example.thevault.domain.transfer;

import com.example.thevault.domain.model.Asset;
import com.example.thevault.domain.model.Klant;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class WelkomDTO {
    private double saldo;
    private List<Asset> portefeuille;
    private String iban;


    @JsonIgnore
    private final Logger logger = LoggerFactory.getLogger(LoginDto.class);

    public WelkomDTO() {
        super();
        logger.info("New WelkomDTO, no args constructor");
    }

    public WelkomDTO(Klant klant){
        super();
        this.saldo = klant.getRekening().getSaldo();
        this.portefeuille= klant.getPortefeuille();
        this.iban = klant.getRekening().getIban();
        logger.info("New WelkomDTO: " + this);
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public List<Asset> getPortefeuille() {
        return portefeuille;
    }

    public void setPortefeuille(List<Asset> portefeuille) {
        this.portefeuille = portefeuille;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }
}
