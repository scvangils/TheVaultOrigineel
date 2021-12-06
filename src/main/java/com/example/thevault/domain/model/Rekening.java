// Created by carme
// Creation date 30/11/2021

package com.example.thevault.domain.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Rekening {

    private  int rekeningId;
    private String iban;
    private double saldo;
    private Klant klant; // voor het klantId


    private final Logger logger = LoggerFactory.getLogger(Rekening.class);

    public Rekening() {
        super();
        logger.info("Lege rekening, no args constructor");
    }

    public Rekening(String iban, double saldo) {
        this.klant = null;
        this.rekeningId = 0;
        this.iban = iban;
        this.saldo = saldo;
        logger.info("Nieuwe rekening " + this + " gemaakt");
    }


    public int getRekeningId() {
        return rekeningId;
    }

    public void setRekeningId(int rekeningId) {
        this.rekeningId = rekeningId;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public void setKlant(Klant klant) {
        this.klant = klant;
    }

    @Override
    public String toString() {
        return "Rekening{" +
                "rekeningId=" + rekeningId +
                ", iban='" + iban + '\'' +
                ", saldo=" + saldo +
                '}';
    }


}
