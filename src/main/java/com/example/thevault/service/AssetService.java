// Created by carme
// Creation date 06/12/2021

package com.example.thevault.service;

import com.example.thevault.domain.mapping.repository.RootRepository;
import com.example.thevault.domain.model.Asset;
import com.example.thevault.domain.model.Cryptomunt;
import com.example.thevault.domain.transfer.AssetDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Carmen Rietdijk
 * Beschrijving: de servicelaag voor asset, waarin informatie over assets kan worden opgeslagen, aangepast en opgevraagd
 * inclusief het vullen van de portefeuille
 */


@Service
public class AssetService {

    private RootRepository rootRepository;
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
     * Er wordt een lijst met alle assets van de klant gemaakt en doorgegeven, waarbij alleen de benodigde informatie
     * voor de klant wordt doorgegeven
     * @param gebruikerId identifier van de klant die de portefeuille oproept
     * @return List</AssetDto> een lijst met alle assets van de klant, zijnde de portefeuille, in de vorm die voor de
     * klant meerwaarde heeft
     */
    public List<AssetDto> geefInhoudPortefeuille(int gebruikerId){
        List<AssetDto> portefeuilleVoorKlant = new ArrayList<>();
        List<Asset> portefeuilleVolledig = rootRepository.vulPortefeuilleKlant(gebruikerId);
        for (Asset asset : portefeuilleVolledig) {
            portefeuilleVoorKlant.add(new AssetDto(asset));
        }
        return portefeuilleVoorKlant;
    }

    /**
     * Er wordt een specifieke asset in de portefeuille opgeroepen en doorgegeven, waarbij alleen de benodigde
     * informatie voor de klant wordt doorgegeven
     * @param gebruikerId identifier van de klant die informatie opvraagt over de cryptomunt
     * @param cryptomuntId identifier waarover informatie wordt opgevraagd
     * @return AssetDto de asset waarover informatie is opgevraagd, in de vorm die voor de klant meerwaarde heeft
     */
    public AssetDto geefCryptomunt(int gebruikerId, int cryptomuntId){
        return new AssetDto(rootRepository.geefAssetVanKlant(gebruikerId, cryptomuntId));
    }

    /**
     * Informatie over een asset van de klant wordt opgeslagen, de informatie is afkomstig uit een transactie
     * @param gebruikerId indentifier van de klant die een asset wil opslaan
     * @param asset de asset die wordt opgeslagen
     * @return Asset de asset die is opgeslagen
     */
    public Asset slaAssetOp(int gebruikerId, Asset asset){
        return rootRepository.slaAssetVanKlantOp(gebruikerId, asset);
    }

    /**
     * De klant wil informatie over een asset in zijn/haar portefeuille aanpassen, op basis van een transactie
     * @param gebruikerId identifier van de klant die een wijziging aan de asset wil doorvoeren
     * @param asset de asset die moet worden aangepast
     * @return Asset de asset die is aangepast
     */
    public Asset wijzigAsset(int gebruikerId, Asset asset){
        return rootRepository.wijzigAssetVanKlant(gebruikerId, asset);
    }

    public RootRepository getRootRepository() {
        return rootRepository;
    }
}
