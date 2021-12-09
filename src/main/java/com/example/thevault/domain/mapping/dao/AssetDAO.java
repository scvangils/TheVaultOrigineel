package com.example.thevault.domain.mapping.dao;

import com.example.thevault.domain.model.Asset;

import java.util.List;

/**
 * @Author: Carmen Rietdijk
 * Beschrijving: De interface voor de DAO voor Asset, waar methodes in staan voor CRUD van de Asset van de klant,
 * alsmede de methode om de portefeuille van de klant te vullen met Assets
 */

public interface AssetDAO {

    /**
     * Dit betreft het toevoegen van een cryptomunt die nog niet in de portefeuille zit
     * Dit gebeurt via een 'transactie', waarbij een klant crypto's koopt
     * @param gebruikerId identifier van de klant die de cryptomunt koopt
     * @param asset de cryptomunt en het aantal dat de klant aanschaft
     * @return Asset de asset die de klant heeft toegevoegd
     */
    public Asset voegNieuwAssetToeAanPortefeuille(int gebruikerId, Asset asset);

    /**
     * Dit betreft het verwijderen van een cryptomunt uit de portefeuille
     * Dit gebeurt via een 'transactie', waarbij een klant crypto's verkoopt
     * @param gebruikerId identifier van de klant die de cryptomunt verkoopt
     * @param asset de asset die uit de portefeuille wordt verwijderd
     * @return String bericht dat de cryptomunt uit de portefeuille is verwijderd
     */
    public Asset verwijderAssetUitPortefeuille(int gebruikerId, Asset asset);

    /**
     * Dit betreft het updaten van een cryptomunt die al in de portefeuille zit
     * Dit gebeurt via een 'transactie', waarbij een klant crypto's koopt of verkoopt
     * @param gebruikerId identifier van de klant die de cryptomunt koopt/verkoopt
     * @param asset de asset waarin de klant handelt
     * @return Asset de asset na de update
     */
    public Asset updateAsset(int gebruikerId, Asset asset);

    /**
     * Dit betreft het vinden van een cryptomunt die in de portefeuille zit
     * @param gebruikerId identifier van de klant die informatie opvraagt over de cryptomunt
     * @param cryptomuntId identifier waarover informatie wordt opgevraagd
     * @return Asset de asset (cryptomunt + aantal) waarover informatie is opgevraagd
     */
    public Asset geefAsset(int gebruikerId, int cryptomuntId);

    /**
     * Dit betreft het vinden van alle cryptomunten die in de portefeuille zitten
     * @param gebruikerId identifier van de klant die informatie opvraagt over de cryptomunt
     * @return List</Asset> een lijst van alle Assets (cryptomunten + hoeveelheden) in het bezit van de klant
     */
    public List<Asset> geefAlleAssets(int gebruikerId);
}
