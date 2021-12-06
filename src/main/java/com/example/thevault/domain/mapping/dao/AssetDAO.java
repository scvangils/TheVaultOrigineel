package com.example.thevault.domain.mapping.dao;

import com.example.thevault.domain.model.Asset;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @Author: Carmen Rietdijk
 * Beschrijving: De interface voor de DAO voor Asset, waar methodes in staan voor CRUD van de Asset van de klant,
 * alsmede de methode om de portefeuille van de klant te vullen met Assets
 */

public interface AssetDAO {

    public Asset voegNieuwAssetToeAanPortefeuille(int klantId, Asset asset);

    public String verwijderAssetUitPortefeuille(int klantId, int cryptomuntId);

    public Asset updateAsset(int klantId, Asset asset);

    public Asset geefAsset(int klantId, int cryptomuntId);

    public List<Asset> geefAlleAssets(int klantId);
}
