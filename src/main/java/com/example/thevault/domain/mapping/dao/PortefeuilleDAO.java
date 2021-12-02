package com.example.thevault.domain.mapping.dao;

import com.example.thevault.domain.model.Cryptomunt;
import com.example.thevault.domain.model.Portefeuille;

import java.util.Map;

public interface PortefeuilleDAO {

    public Portefeuille vindPortefeuilleInhoudMetID(int klantId);

    public void voegCryptomuntToe(int cryptomuntId, int klantId);

    public void verwijderCryptomunt(int cryptomuntId, int klantId);

    public void updateSaldoCryptomunt(int cryptomuntId, int hoeveelheid, int klantId);
}
