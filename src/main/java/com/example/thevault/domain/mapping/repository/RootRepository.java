// Created by carme
// Creation date 01/12/2021

package com.example.thevault.domain.mapping.repository;

import com.example.thevault.domain.mapping.dao.AssetDAO;
import com.example.thevault.domain.mapping.dao.KlantDAO;
import com.example.thevault.domain.mapping.dao.RekeningDAO;
import com.example.thevault.domain.model.Asset;
import com.example.thevault.domain.model.Cryptomunt;
import com.example.thevault.domain.model.Klant;
import com.example.thevault.domain.model.Rekening;
import net.minidev.json.annotate.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
public class RootRepository {

    @JsonIgnore
    private final Logger logger = LoggerFactory.getLogger(RootRepository.class);

    private final KlantDAO klantDAO;
    private final RekeningDAO rekeningDAO;
    private final AssetDAO assetDAO;

    public RootRepository(KlantDAO klantDAO, RekeningDAO rekeningDAO, AssetDAO assetDAO) {
        super();
        this.rekeningDAO = rekeningDAO;
        this.klantDAO = klantDAO;
        this.assetDAO = assetDAO;
        logger.info("New RootRepository");
    }

    public Klant slaKlantOp(Klant klant){
        return klantDAO.slaKlantOp(klant);
    }

    public Klant vindKlantByGebruikersnaam(String username){
        return klantDAO.vindKlantByGebruikersnaam(username);
    }

    /**
     * Deze methode slaat de gegevens van een rekening op in de database via de methode in de rekeningDAO.
     *
     * @param rekening is de rekening die is aangemaakt bij een nieuwe klant.
     * @return de rekening behorend bij de nieuwe klant met klant-id
     */
    public Rekening slaRekeningOp(Rekening rekening){
        rekeningDAO.slaRekeningOp(rekening);
        return rekening;
    }

    /**
     * Deze methode vindt de rekening van klant in de database via de methode in de rekeningDAO.
     *
     * @param klant is de klant van wie de rekening wordt opgevraagd.
     * @return de rekening behorend bij de klant met klant-id
     */
    public Rekening vindRekeningVanKlant(Klant klant){
        return rekeningDAO.vindRekeningVanKlant(klant);
    }

    /**
     * Deze methode geeft het rekeningsaldo op van de klant in de database via de methode in de rekeningDAO.
     *
     * @param klant is de klant van wie het rekeningsaldo wordt opgevraagd.
     * @return het rekeningsaldo behorend bij de de klant met klant-id
     */
    public double vraagSaldoOpVanKlant(Klant klant){
        return rekeningDAO.vraagSaldoOpVanKlant(klant);
    }

    /**
     * Deze methode wijzigt het rekeningsaldo van de klant in de database via de methode in de rekeningDAO.
     *
     * @param klant is de klant van wie het rekeningsaldo wordt opgevraagd.
     * @param bedrag is het bedrag waarnaar het saldo gewijzigd moet worden.
     * @return het rekeningsaldo behorend bij de de klant met klant-id wordt gewijzigd.
     */
    public Rekening wijzigSaldoVanKlant(Klant klant, double bedrag){
        rekeningDAO.wijzigSaldoVanKlant(klant, bedrag);
        return rekeningDAO.vindRekeningVanKlant(klant);
    }

    /**
     * @Author Carmen
     * Dit betreft het vullen van de portefeuille met alle cryptomunten die er in zitten
     * @param klantId identifier van de klant die informatie opvraagt over de cryptomunt
     * @return List</Asset> een lijst van alle Assets (cryptomunten + hoeveelheden) in het bezit van de klant
     */
    public List<Asset> vulPortefeuilleKlant(int klantId){
        return assetDAO.geefAlleAssets(klantId);
    }

    /**
     * Dit betreft het vinden van een specifieke cryptomunt die in de portefeuille zit
     * @param klantId identifier van de klant die informatie opvraagt over de cryptomunt
     * @param cryptomuntId identifier waarover informatie wordt opgevraagd
     * @return Asset de asset (cryptomunt + aantal) waarover informatie is opgevraagd
     */
    public Asset geefAssetVanKlant(int klantId, int cryptomuntId){
        return assetDAO.geefAsset(klantId, cryptomuntId);
    }

    /**
     * Dit betreft het toevoegen van een cryptomunt die nog niet in de portefeuille zit
     * Dit gebeurt via een 'transactie', waarbij een klant crypto's koopt
     * @param klantId identifier van de klant die de cryptomunt koopt
     * @param asset de cryptomunt en het aantal dat de klant aanschaft
     * @return Asset de asset die de klant heeft toegevoegd
     */
    public Asset slaAssetVanKlantOp(int klantId, Asset asset){
        if(assetDAO.geefAsset(klantId, asset.getCryptomunt().getId()) == null){
            return assetDAO.voegNieuwAssetToeAanPortefeuille(klantId, asset);
        } else {
            return assetDAO.updateAsset(klantId, asset);
        }
    }

    /**
     * Dit betreft het wijzigen van een cryptomunt die al in de portefeuille zit
     * Dit gebeurt via een 'transactie', waarbij een klant crypto's koopt of verkoopt
     * @param klantId identifier van de klant die de cryptomunt koopt/verkoopt
     * @param asset de asset waarin de klant handelt, met de informatie wélke cryptomunt wordt verhandeld
     *              en hoeveel deze omhoog/omlaag gaat (oftewel: betreft het een koop of een verkoop)
     * @return Asset de asset na de update, waarbij het nieuwe aantal wordt meegegeven
     */
    public Asset wijzigAssetVanKlant(int klantId, Asset asset){
        return assetDAO.updateAsset(klantId, asset);
    }
}
