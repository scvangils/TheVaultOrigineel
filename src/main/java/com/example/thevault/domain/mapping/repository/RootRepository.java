// Created by carme
// Creation date 01/12/2021

package com.example.thevault.domain.mapping.repository;

import com.example.thevault.domain.mapping.dao.KlantDAO;
import com.example.thevault.domain.mapping.dao.RekeningDAO;
import com.example.thevault.domain.model.Cryptomunt;
import com.example.thevault.domain.model.Klant;
import com.example.thevault.domain.model.Rekening;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class RootRepository {

    private final Logger logger = LoggerFactory.getLogger(RootRepository.class);

    private final KlantDAO klantDAO;
    private final RekeningDAO rekeningDAO;

    public RootRepository(KlantDAO klantDAO, RekeningDAO rekeningDAO) {
        super();
        this.rekeningDAO = rekeningDAO;
        this.klantDAO = klantDAO;
        logger.info("New RootRepository");
    }

    public Map<Cryptomunt,Double> geefAlleCryptomuntenVanKlant(int klantId){
        //Roept via de PortefeuilleDAO (of klantDAO?) informatie op vanuit de tussentabel 'portefeuille' obv de klantId
        return null;
    }

    public double geefHoeveelheidCryptomuntVanKlant(int cryptomuntId, int klantId){
        //Roept via de PortefeuilleDAO (of klantDAO?) informatie op vanuit de tussentabel 'portefeuille' obv combinatie
        // klantId en cryptomuntId
        return 0.0;
    }
    public void slaKlantOp(Klant klant){
        klantDAO.slaKlantOp(klant);

    }
    public Klant vindKlantByUsername(String username){
        return klantDAO.vindKlantByUsername(username);
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
}
