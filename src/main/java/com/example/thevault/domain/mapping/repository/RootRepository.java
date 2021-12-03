// Created by carme
// Creation date 01/12/2021

package com.example.thevault.domain.mapping.repository;

import com.example.thevault.domain.mapping.dao.AssetDAO;
import com.example.thevault.domain.mapping.dao.JDBCAssetDAO;
import com.example.thevault.domain.mapping.dao.KlantDAO;
import com.example.thevault.domain.model.Asset;
import com.example.thevault.domain.model.Cryptomunt;
import com.example.thevault.domain.model.Klant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class RootRepository {

    private final Logger logger = LoggerFactory.getLogger(RootRepository.class);

    private final KlantDAO klantDAO;
    private final AssetDAO assetDAO;

    public RootRepository(KlantDAO klantDAO, AssetDAO assetDAO) {
        super();
        this.klantDAO = klantDAO;
        this.assetDAO = assetDAO;
        logger.info("New RootRepository");
    }

    public List<Asset> vulPortefeuilleKlant(int klantId){
        return assetDAO.geefAlleAssets(klantId);
    }

    public Asset geefAssetVanKlant(int klantId, int cryptomuntId){
        return assetDAO.geefAsset(klantId, cryptomuntId);
    }

    public void slaKlantOp(Klant klant){
        klantDAO.slaKlantOp(klant);

    }
    public Klant vindKlantByUsername(String username){
        return klantDAO.vindKlantByUsername(username);
    }
}
