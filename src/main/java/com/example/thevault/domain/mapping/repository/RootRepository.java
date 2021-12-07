// Created by carme
// Creation date 01/12/2021

package com.example.thevault.domain.mapping.repository;

import com.example.thevault.domain.mapping.dao.AssetDAO;
import com.example.thevault.domain.mapping.dao.KlantDAO;
import com.example.thevault.domain.model.Asset;
import com.example.thevault.domain.mapping.dao.RekeningDAO;
import com.example.thevault.domain.model.Klant;
import com.example.thevault.domain.model.Rekening;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
public class RootRepository {

    private final Logger logger = LoggerFactory.getLogger(RootRepository.class);

    private final KlantDAO klantDAO;
    private final AssetDAO assetDAO;
    private final RekeningDAO rekeningDAO;

    public RootRepository(KlantDAO klantDAO, AssetDAO assetDAO, RekeningDAO rekeningDAO) {
        super();
        this.klantDAO = klantDAO;
        this.assetDAO = assetDAO;
        this.rekeningDAO = rekeningDAO;
        logger.info("New RootRepository");
    }

    /**
     * Deze methode slaat de gegevens van een klant op in de database
     * via de methode in de KlantDAO
     *
     * @param klant het klant-object op basis van bij registratie ingevoerde gegevens
     * @return het klant-object met de juiste gebruikerId
     */
    public Klant slaKlantOp(Klant klant){
        return klantDAO.slaKlantOp(klant);
    }
    /**
     * Deze methode zoekt of er in de database al een klant bestaat met deze gebruikerId
     * en maakt eventueel een klant-object aan op nasis van de teruggestuurde gegevens
     *
     * @param gebruikerId gebruikerId van een (mogelijke) klant die uniek moet zijn
     * @return klant-object op basis van gegevens uit de database of null indien gebruikerId niet gevonden is
     */
    public Klant vindKlantById(int gebruikerId){
        return klantDAO.vindKlantById(gebruikerId);
    }
    /**
     * Deze methode zoekt of er in de database al een klant bestaat met deze gebruikersnaam
     * en maakt eventueel een klant-object aan op nasis van de teruggestuurde gegevens
     *
     * @param gebruikersnaam gebruikersnaam van een (mogelijke) klant die uniek moet zijn
     * @return klant-object op basis van gegevens uit de database of null indien gebruikersnaam niet gevonden is
     */
    public Klant vindKlantByGebruikersnaam(String gebruikersnaam){
        return klantDAO.vindKlantByGebruikersnaam(gebruikersnaam);
    }

    public void slaRekeningOp(Rekening rekening){
        rekeningDAO.slaRekeningOp(rekening);
    }

    public Rekening vindRekeningVanKlant(Klant klant){
        return rekeningDAO.vindRekeningVanKlant(klant);
    }

    public double vraagSaldoOpVanKlant(Klant klant){
        return rekeningDAO.vraagSaldoOpVanKlant(klant);
    }

    public void wijzigSaldoVanKlant(Klant klant, double bedrag){
        rekeningDAO.wijzigSaldoVanKlant(klant, bedrag);
    }

    public List<Asset> vulPortefeuilleKlant(int klantId) throws SQLException {
        return assetDAO.geefAlleAssets(klantId);
    }

    public Asset geefAssetVanKlant(int klantId, int cryptomuntId){
        return assetDAO.geefAsset(klantId, cryptomuntId);
    }

    public Asset slaAssetVanKlantOp(int klantId, Asset asset){
        if(assetDAO.geefAsset(klantId, asset.getCryptomunt().getId()) == null){
            return assetDAO.voegNieuwAssetToeAanPortefeuille(klantId, asset);
        } else {
            return assetDAO.updateAsset(klantId, asset);
        }
    }

    public Asset wijzigAssetVanKlant(int klantId, Asset asset){
        return assetDAO.updateAsset(klantId, asset);
    }
}
