// Created by carme
// Creation date 01/12/2021

package com.example.thevault.domain.mapping.repository;

import com.example.thevault.domain.model.Cryptomunt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class RootRepository {

    private final Logger logger = LoggerFactory.getLogger(RootRepository.class);

    public RootRepository() {
        super();
        logger.info("New RootRepository");
    }

    public Map<Cryptomunt,Double> geefAlleCryptomuntenVanKlant(int klantId){
        //Roept via de MySQLPortefeuilleDAO (of klantDAO?) informatie op vanuit de tussentabel 'portefeuille' obv de klantId
    }

    public double geefHoeveelheidCryptomuntVanKlant(int cryptomuntId, int klantId){
        //Roept via de MySQLPortefeuilleDAO (of klantDAO?) informatie op vanuit de tussentabel 'portefeuille' obv combinatie
        // klantId en cryptomuntId
    }
}
