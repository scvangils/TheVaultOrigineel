// Created by carme
// Creation date 06/12/2021

package com.example.thevault.service;

import com.example.thevault.domain.mapping.repository.RootRepository;
import com.example.thevault.domain.model.*;
import com.example.thevault.domain.transfer.AssetDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author: Carmen
 * Beschrijving: de servicelaag voor asset, waarin informatie over assets kan worden opgeslagen, aangepast en opgevraagd
 * inclusief het vullen van de portefeuille
 */

@Service
public class AssetService {

    private RootRepository rootRepository;

    @JsonIgnore
    private final Logger logger = LoggerFactory.getLogger(AssetService.class);

    /**
     * Hier wordt de assetService aangemaakt, met de rootrepository geïnjecteerd
     * @param rootRepository de repository waar de methodes kunnen worden aangeroepen die in deze class worden gebruikt
     */
    @Autowired
    public AssetService(RootRepository rootRepository) {
        super();
        this.rootRepository = rootRepository;
        logger.info("New AssetService");
    }

    /**
     * Er wordt een specifieke asset in de portefeuille opgeroepen en doorgegeven, waarbij alleen de benodigde
     * informatie voor de klant wordt doorgegeven
     * @param gebruiker de gebruiker die informatie opvraagt over de cryptomunt
     * @param cryptomunt cryptomunt waarover informatie wordt opgevraagd
     * @return AssetDto de asset waarover informatie is opgevraagd, in de vorm die voor de klant meerwaarde heeft
     */
    public AssetDto geefCryptomunt(Gebruiker gebruiker, Cryptomunt cryptomunt, CryptoWaarde cryptowaarde){
        return new AssetDto(rootRepository.geefAssetVanGebruiker(gebruiker, cryptomunt), cryptowaarde);
    }

    /**
     * Informatie over een asset van de klant wordt opgeslagen, de informatie is afkomstig uit een transactie
     * @param asset de asset die wordt opgeslagen
     * @return Asset de asset die is opgeslagen
     */
    public Asset slaNieuwAssetOp(Asset asset){
        return rootRepository.slaNieuwAssetVanKlantOp(asset);
    }

    /**
     * De klant wil informatie over een asset in zijn/haar portefeuille aanpassen, op basis van een transactie
     * @param gebruiker de handelende partij
     * @param cryptomunt de munt waarin gehandeld wordt
     * @param aantal de hoeveelheid die verhandeld wordt
     * @return Asset de asset die is aangepast
     */
    public Asset wijzigAssetGebruiker(Gebruiker gebruiker, Cryptomunt cryptomunt, double aantal){
        List<Asset> portefeuille = vulPortefeuilleVanGebruiker(gebruiker);
        if(portefeuille != null) {
        return rootRepository.wijzigAssetVanKlant(gebruiker, cryptomunt, aantal);
        }
        return null;
    }

    /**
     * De klant vraagt de informatie van zijn/haar portefeuille op
     * @param gebruiker de handelende partij
     * @return List<Asset> alle Assets van de klant
     */
    public List<Asset> vulPortefeuilleVanGebruiker(Gebruiker gebruiker){
        return rootRepository.vulPortefeuilleKlant(gebruiker);
    }

    /**
     * Er wordt een lijst van alle cryptomunten opgevraagd
     * @return List<Cryptomunt> alle cryptomunten in de database
     */
    public List<Cryptomunt> geefAlleCryptomunten(){
        return rootRepository.geefAlleCryptomunten();
    }
}
