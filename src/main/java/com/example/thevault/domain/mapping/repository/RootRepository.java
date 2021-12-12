// Created by carme
// Creation date 01/12/2021

package com.example.thevault.domain.mapping.repository;

import com.example.thevault.domain.mapping.dao.AssetDAO;
import com.example.thevault.domain.mapping.dao.CryptomuntDAO;
import com.example.thevault.domain.mapping.dao.KlantDAO;
import com.example.thevault.domain.mapping.dao.RekeningDAO;
import com.example.thevault.domain.model.Asset;
import com.example.thevault.domain.model.Cryptomunt;
import com.example.thevault.domain.model.Klant;
import com.example.thevault.domain.model.Rekening;
import net.minidev.json.annotate.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RootRepository {

    @JsonIgnore
    private final Logger logger = LoggerFactory.getLogger(RootRepository.class);

    private final KlantDAO klantDAO;
    private final RekeningDAO rekeningDAO;
    private final AssetDAO assetDAO;
    private final CryptomuntDAO cryptomuntDAO;


    @Autowired
    public RootRepository(KlantDAO klantDAO, RekeningDAO rekeningDAO, AssetDAO assetDAO, CryptomuntDAO cryptomuntDAO) {
        super();
        this.rekeningDAO = rekeningDAO;
        this.klantDAO = klantDAO;
        this.assetDAO = assetDAO;
        this.cryptomuntDAO = cryptomuntDAO;
        logger.info("New RootRepository");
    }

    public Klant slaKlantOp(Klant klant){
        return klantDAO.slaKlantOp(klant);
    }

    public Klant vindKlantByGebruikersnaam(String username){
        Klant klant = klantDAO.vindKlantByGebruikersnaam(username);
        klant.setPortefeuille(vulPortefeuilleKlant(klant));
        //TODO rekening toevoegen na database compleet
        return klant;
    }

    /**
     * Deze methode slaat de gegevens van een rekening op in de database via de methode in de rekeningDAO.
     *
     * @param rekening is de rekening die is aangemaakt bij een nieuwe klant.
     * @return de rekening behorend bij de nieuwe klant met klant-id
     */
    public Rekening slaRekeningOp(Rekening rekening){
        return rekeningDAO.slaRekeningOp(rekening);
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
     * @return het rekeningsaldo behorend bij de klant met klant-id
     */
    public double vraagSaldoOpVanKlant(Klant klant){
        return rekeningDAO.vraagSaldoOpVanKlant(klant);
    }

    /**
     * Deze methode wijzigt het rekeningsaldo van de klant in de database via de methode in de rekeningDAO.
     *
     * @param klant is de klant van wie het rekeningsaldo wordt opgevraagd.
     * @param bedrag is het bedrag waarnaar het saldo gewijzigd moet worden.
     * @return het rekeningsaldo behorend bij de klant met klant-id wordt gewijzigd.
     */
    public Rekening wijzigSaldoVanKlant(Klant klant, double bedrag){
        return rekeningDAO.wijzigSaldoVanKlant(klant, bedrag);
    }

    /**
     * @Author Carmen
     * Dit betreft het vullen van de portefeuille met alle cryptomunten die er in zitten. Voor iedere asset
     * wordt alle informatie over de bijbehorende cryptomunt opgevraagd en meegegeven
     * @param klant de klant die informatie opvraagt over de cryptomunt
     * @return List</Asset> een lijst van alle Assets (cryptomunten + hoeveelheden) in het bezit van de klant
     */
    public List<Asset> vulPortefeuilleKlant(Klant klant){
        List<Asset> portefeuille = assetDAO.geefAlleAssets(klant);
        for (Asset asset: portefeuille) {
            asset.setCryptomunt(cryptomuntDAO.geefCryptomunt(asset.getCryptomunt().getId()));
        }
        return portefeuille;
    }

    /**
     * Dit betreft het vinden van een specifieke cryptomunt die in de portefeuille zit
     * @param klant de klant die informatie opvraagt over de cryptomunt
     * @param cryptomunt cryptomunt waarover informatie wordt opgevraagd
     * @return Asset de asset (cryptomunt + aantal) waarover informatie is opgevraagd
     */
    public Asset geefAssetVanKlant(Klant klant, Cryptomunt cryptomunt){
        return assetDAO.geefAsset(klant, cryptomunt);
    }

    /**
     * Dit betreft het toevoegen van een cryptomunt die nog niet in de portefeuille zit
     * Dit gebeurt via een 'transactie', waarbij een klant crypto's koopt
     * @param klant de klant die de cryptomunt koopt
     * @param asset de cryptomunt en het aantal dat de klant aanschaft
     * @return Asset de asset die de klant heeft toegevoegd
     */
    public Asset slaAssetVanKlantOp(Klant klant, Asset asset){
        if(assetDAO.geefAsset(klant, asset.getCryptomunt()) == null){
            return assetDAO.voegNieuwAssetToeAanPortefeuille(asset);
        } else {
            return assetDAO.updateAsset(asset);
        }
    }

    /**
     * Dit betreft het wijzigen van een cryptomunt die al in de portefeuille zit
     * Dit gebeurt via een 'transactie', waarbij een klant crypto's koopt of verkoopt
     * @param asset de asset waarin de klant handelt, met de informatie wélke cryptomunt wordt verhandeld
     *              en hoeveel deze omhoog/omlaag gaat (oftewel: betreft het een koop of een verkoop)
     * @return Asset de asset na de update, waarbij het nieuwe aantal wordt meegegeven
     */
    public Asset wijzigAssetVanKlant(Asset asset){
        return assetDAO.updateAsset(asset);
    }
}
