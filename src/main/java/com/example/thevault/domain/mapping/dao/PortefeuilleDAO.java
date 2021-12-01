package com.example.thevault.domain.mapping.dao;

import com.example.thevault.domain.model.Cryptomunt;
import com.example.thevault.domain.model.Portefeuille;

import java.util.Map;

public interface PortefeuilleDAO {

    public Portefeuille vindPortefeuilleInhoudMetID(int klantId);

    public void voegCryptomuntToe(int cryptomuntId);

    public void verwijderCryptomunt(int cryptomuntId);

    public void updateSaldoCryptomunt(int cryptomuntId, int hoeveelheid);
}
