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

    public void slaKlantOp(Klant klant){
        klantDAO.slaKlantOp(klant);
    }

    public Klant vindKlantByUsername(String username){
        return klantDAO.vindKlantByGebruikersnaam(username);
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

    /**
     * @Author Carmen
     * De informatie van alle Assets van de klant wordt als List weergegeven, wat de portefeuille van de klant vormt
     * @param klantId identifier van de klant die de portefeuille opvraagt
     * @return List</Asset> een lijst van alle Assets van de klant
     * @throws SQLException
     */
    public List<Asset> vulPortefeuilleKlant(int klantId) throws SQLException {
        return assetDAO.geefAlleAssets(klantId);
    }

    /**
     * @Author Carmen
     * De klant krijgt informatie te zien over een specifieke Asset die hij/zij bezit
     * @param klantId
     * @param cryptomuntId
     * @return Asset de asset waarover de klant informatie heeft opgevraagd
     */
    public Asset geefAssetVanKlant(int klantId, int cryptomuntId){
        return assetDAO.geefAsset(klantId, cryptomuntId);
    }

    /**
     * @Author Carmen
     * Informatie over een asset van de klant wordt opgeslagen, op basis van een transactie. Als de cryptomunt nog niet
     * in de portefeuille zit wordt deze als nieuwe asset opgeslagen, en anders wordt de bestaande asset geüpdate
     * @param klantId indentifier van de klant die een asset wil opslaan
     * @param asset de asset die wordt opgeslagen
     * @return Asset de asset die is opgeslagen
     */
    public Asset slaAssetVanKlantOp(int klantId, Asset asset){
        if(assetDAO.geefAsset(klantId, asset.getCryptomunt().getCryptomuntId()) == null){
            return assetDAO.voegNieuwAssetToeAanPortefeuille(klantId, asset);
        } else {
            return assetDAO.updateAsset(klantId, asset);
        }
    }

    /**
     * @Author Carmen
     * De klant wil informatie over een asset in zijn/haar portefeuille aanpassen, dit gebeurt op basis van een
     * transactie
     * @param klantId identifier van de klant die een wijziging aan de asset wil doorvoeren
     * @param asset de asset die moet worden aangepast
     * @return Asset de asset die is aangepast
     */
    public Asset wijzigAssetVanKlant(int klantId, Asset asset){
        return assetDAO.updateAsset(klantId, asset);
    }
}
