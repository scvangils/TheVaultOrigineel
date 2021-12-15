// Created by E.S. Olthof
// Creation date 12/15/2021

package com.example.thevault.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Objects;

public class Transactie {
    private int transactieId;
    private double aantal;
    private OffsetDateTime momentTransactie;
    private double bedrag;
    private Klant koper;
    private Klant verkoper;
    private Cryptomunt cryptomunt;

    @JsonIgnore
    private final Logger logger = LoggerFactory.getLogger(Rekening.class);

    public Transactie() {
        super();
        logger.info("lege Transactie, no args constructor");
    }


    public Transactie(OffsetDateTime momentTransactie,
                      Klant verkoper, Cryptomunt cryptomunt, double bedrag, double aantal,
                      Klant koper) {
        this.aantal = aantal;
        this.momentTransactie = momentTransactie;
        this.cryptomunt = cryptomunt;
        this.bedrag = bedrag;
        this.koper = koper;
        this.verkoper = verkoper;
        logger.info("New "+ this + " aangemaakt");
    }


    public int getTransactieId() {
        return transactieId;
    }

    public void setTransactieId(int transactieId) {
        this.transactieId = transactieId;
    }

    public double getAantal() {
        return aantal;
    }

    public void setAantal(double aantal) {
        this.aantal = aantal;
    }

    public OffsetDateTime getMomentTransactie() {
        return momentTransactie;
    }

    public void setMomentTransactie(OffsetDateTime momentTransactie) {
        this.momentTransactie = momentTransactie;
    }

    public double getBedrag() {
        return bedrag;
    }

    public void setBedrag(double bedrag) {
        this.bedrag = bedrag;
    }

    public Klant getKoper() {
        return koper;
    }

    public void setKoper(Klant koper) {
        this.koper = koper;
    }

    public Klant getVerkoper() {
        return verkoper;
    }

    public void setVerkoper(Klant verkoper) {
        this.verkoper = verkoper;
    }

    public Cryptomunt getCryptomunt() {
        return cryptomunt;
    }

    public void setCryptomunt(Cryptomunt cryptomunt) {
        this.cryptomunt = cryptomunt;
    }

    @Override
    public String toString() {
        return "Transactie{" +
                "transactieId=" + transactieId +
                ", aantal=" + aantal +
                ", datumTijd=" + momentTransactie +
                ", bedrag=" + bedrag +
                ", koper=" + koper +
                ", verkoper=" + verkoper +
                ", cryptomunt=" + cryptomunt +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transactie)) return false;
        Transactie that = (Transactie) o;
        return getTransactieId() == that.getTransactieId()
                && Double.compare(that.getAantal(), getAantal()) == 0
                && Double.compare(that.getBedrag(), getBedrag()) == 0
                && getMomentTransactie().equals(that.getMomentTransactie())
                && getKoper().equals(that.getKoper())
                && getVerkoper().equals(that.getVerkoper())
                && getCryptomunt().equals(that.getCryptomunt());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTransactieId(), getAantal(), getMomentTransactie()
                , getBedrag(), getKoper(), getVerkoper(), getCryptomunt());
    }
}
