package com.example.thevault.domain.mapping.dao;

import com.example.thevault.domain.model.Asset;

import java.util.List;

public interface AssetDAO {

    public void voegNieuwAssetToeAanPortefeuille(int klantId, Asset asset);

    public void verwijderAssetUitPortefeuille(int klantId, int cryptomuntId);

    public void updateAsset(int klantId, int cryptomuntId, double aantal);

    public Asset geefAsset(int klantId, int cryptomuntId);

    public List<Asset> geefAlleAssets(int klantId);
}
