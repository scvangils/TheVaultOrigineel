// Created by carme
// Creation date 30/11/2021

package com.example.thevault.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Bank extends Gebruiker{

    private static Bank instance = new Bank(null, null);

    private String banknaam;
    private String bankcode;
    private final String BANKNAAM = "The Vault";
    public final static String BANKCODE = "TVLT";
    private final String BANK_GEBRUIKERSNAAM = "BankToTheFuture";
    private final String BANK_WACHTWOORD = "!youvegottacomebankwithme!";
    private Rekening rekening;
    private List<Asset> portefeuille;

    @JsonIgnore
    private final Logger logger = LoggerFactory.getLogger(Bank.class);


    //TODO hoe zit dit i.c.m. Spring
    private Bank() {
        super();
        logger.info("New Bank");
    }
    private Bank(Rekening rekening, List<Asset> portefeuille){
        super();
        this.gebruikersnaam = BANK_GEBRUIKERSNAAM;
        this.wachtwoord = BANK_WACHTWOORD;
        this.banknaam = BANKNAAM;
        this.bankcode = BANKCODE;
        this.rekening = rekening;
        this.portefeuille = portefeuille;
    }
    public static Bank getInstance(){
        return instance;
    }

    public String getBanknaam() {
        return banknaam;
    }

    public void setBanknaam(String banknaam) {
        this.banknaam = banknaam;
    }

    public String getBankcode() {
        return bankcode;
    }

    public void setBankcode(String bankcode) {
        this.bankcode = bankcode;
    }

    public Rekening getRekening() {
        return rekening;
    }

    public void setRekening(Rekening rekening) {
        this.rekening = rekening;
    }

    public List<Asset> getPortefeuille() {
        return portefeuille;
    }

    public void setPortefeuille(List<Asset> portefeuille) {
        this.portefeuille = portefeuille;
    }
}
