// Created by carme
// Creation date 30/11/2021

package com.example.thevault.domain.model;

import net.minidev.json.annotate.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Rekening {

    private String iban;
    private double saldo;
    private Klant klant;

    @JsonIgnore
    private final Logger logger = LoggerFactory.getLogger(Rekening.class);

    /**
    * De no-args constructor voor Rekening
    */
    public Rekening() {
        super();
        logger.info("Lege rekening, no args constructor");
    }

    /**
     * De all-args constructor voor Rekening
     * @param iban uniek iban-nummer die elke rekening heeft
     * @param saldo het bedrag wat op de rekening staat met cryptomunten te handelen
     */
    public Rekening(String iban, double saldo) {
        this.klant = null;
        this.iban = iban;
        this.saldo = saldo;
        logger.info("Nieuwe rekening " + this + " gemaakt");
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
                ", iban='" + iban + '\'' +
                ", saldo=" + saldo +
                '}';
    }
}
