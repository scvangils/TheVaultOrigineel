// Created by carme
// Creation date 03/12/2021

package com.example.thevault.domain.mapping.dao;

import com.example.thevault.domain.model.Asset;
import com.example.thevault.domain.model.Cryptomunt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JDBCAssetDAO implements AssetDAO{

    private final Logger logger = LoggerFactory.getLogger(JDBCAssetDAO.class);

    public JDBCAssetDAO() {
        super();
        logger.info("New JDBCAssetDAO");
    }

    /**
     * Dit betreft het toevoegen van een cryptomunt die nog niet in de portefeuille zit
     * Dit gebeurt via een 'transactie', waarbij een klant crypto's koopt
     * @param klantId
     * @param asset
     */
    @Override
    public void voegNieuwAssetToeAanPortefeuille(int klantId, Asset asset) {

    }

    /**
     * Dit betreft het verwijderen van een cryptomunt uit de portefeuille
     * Dit gebeurt via een 'transactie', waarbij een klant crypto's verkoopt
     * @param klantId
     * @param cryptomuntId
     */

    @Override
    public void verwijderAssetUitPortefeuille(int klantId, int cryptomuntId) {

    }

    /**
     * Dit betreft het updaten van een cryptomunt die al in de portefeuille zit
     * Dit gebeurt via een 'transactie', waarbij een klant crypto's koopt of verkoopt
     * @param klantId
     * @param cryptomuntId
     * @param aantal
     */

    @Override
    public void updateAsset(int klantId, int cryptomuntId, double aantal) {

    }

    /**
     * Dit betreft het vinden van een cryptomunt die in de portefeuille zit
     * @param klantId
     * @param cryptomuntId
     */

    @Override
    public Asset geefAsset(int klantId, int cryptomuntId) {
        return new Asset();
    }

    /**
     * Dit betreft het vinden van alle cryptomunten die in de portefeuille zitten
     * @param klantId
     */

    public List<Asset> geefAlleAssets(int klantId){
        return new ArrayList<Asset>();
    }
}
