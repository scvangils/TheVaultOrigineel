package com.example.thevault.domain.mapping.dao;

import com.example.thevault.domain.model.Cryptomunt;
import com.example.thevault.domain.model.Portefeuille;

import java.util.Map;

public interface PortefeuilleDAOInterface {

    public void slaOp(Portefeuille portefeuille);

    public Portefeuille vindPortefeuilleMetID(int portefeuilleId);

    public void voegCryptomuntToe(int cryptomuntId);

    public void verwijderCryptomunt(int cryptomuntId);

    public void pasSaldoCryptomuntAan(int cryptomuntId, int hoeveelheid);

    public Map<Cryptomunt,Double> inhoudPortefeuille(int portefeuilleId);


}
