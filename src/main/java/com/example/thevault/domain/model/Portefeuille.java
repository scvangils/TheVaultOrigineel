// Created by carme
// Creation date 30/11/2021

package com.example.thevault.domain.model;

/**
 * @Author: Carmen
 * Note voor databaseimplementatie:
 * - Portefeuille wordt een tussentabel tussen klant/user en cryptomunt
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class Portefeuille {

    int portefeuilleId; //Dit is een attribuut
    Map<Cryptomunt, Double> saldiCryptomunten; //Dit is een referentie, want bevat verwijzing naar andere class

    private final Logger logger = LoggerFactory.getLogger(Portefeuille.class);

    public Portefeuille() {
        super();
        logger.info("Lege portefeuille, no args constructor");
    }

    private Portefeuille(int portefeuilleId, Map<Cryptomunt, Double> saldiCryptomunten) {
        this.portefeuilleId = portefeuilleId;
        this.saldiCryptomunten = saldiCryptomunten;
    }

    public Portefeuille(int portefeuilleId){
        this(portefeuilleId, null);
        logger.info("Nieuwe portefeuille");
    }

    public int getPortefeuilleId() {
        return portefeuilleId;
    }

    public void setPortefeuilleId(int portefeuilleId) {
        this.portefeuilleId = portefeuilleId;
    }

    public Map<Cryptomunt, Double> getSaldiCryptomunten() {
        return saldiCryptomunten;
    }

    public void setSaldiCryptomunten(Map<Cryptomunt, Double> saldiCryptomunten) {
        this.saldiCryptomunten = saldiCryptomunten;
    }

    @Override
    public String toString() {
        return "Portefeuille{" +
                "portefeuilleId=" + portefeuilleId +
                ", saldiCryptomunten=" + saldiCryptomunten +
                '}';
    }

}
