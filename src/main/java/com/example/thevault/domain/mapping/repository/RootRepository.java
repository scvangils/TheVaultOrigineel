// Created by carme
// Creation date 01/12/2021

package com.example.thevault.domain.mapping.repository;

import com.example.thevault.domain.mapping.dao.KlantDAO;
import com.example.thevault.domain.mapping.dao.RekeningDAO;
import com.example.thevault.domain.model.Cryptomunt;
import com.example.thevault.domain.model.Klant;
import com.example.thevault.domain.model.Rekening;
import net.minidev.json.annotate.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class RootRepository {

    @JsonIgnore
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
        //Roept via de MySQLPortefeuilleDAO (of klantDAO?) informatie op vanuit de tussentabel 'portefeuille' obv de klantId
        return null;
    }

    public double geefHoeveelheidCryptomuntVanKlant(int cryptomuntId, int klantId){
        //Roept via de MySQLPortefeuilleDAO (of klantDAO?) informatie op vanuit de tussentabel 'portefeuille' obv combinatie
        // klantId en cryptomuntId
        return 0.0;
    }

    public void slaKlantOp(Klant klant){
        klantDAO.slaKlantOp(klant);
    }

    public Klant vindKlantByGebruikersnaam(String username){
        return klantDAO.vindKlantByGebruikersnaam(username);
    }

    /**
     * Deze methode slaat de gegevens van een rekening op in de database via de methode in de rekeningDAO.
     *
     * @param rekening is de rekening die is aangemaakt bij een nieuwe klant.
     * @return de rekening behorend bij de nieuwe klant met klant-id
     */
    public Rekening slaRekeningOp(Rekening rekening){
        rekeningDAO.slaRekeningOp(rekening);
        return rekening;
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
     * @return het rekeningsaldo behorend bij de de klant met klant-id
     */
    public double vraagSaldoOpVanKlant(Klant klant){
        return rekeningDAO.vraagSaldoOpVanKlant(klant);
    }

    /**
     * Deze methode wijzigt het rekeningsaldo van de klant in de database via de methode in de rekeningDAO.
     *
     * @param klant is de klant van wie het rekeningsaldo wordt opgevraagd.
     * @param bedrag is het bedrag waarnaar het saldo gewijzigd moet worden.
     * @return het rekeningsaldo behorend bij de de klant met klant-id wordt gewijzigd.
     */
    public Rekening wijzigSaldoVanKlant(Klant klant, double bedrag){
        rekeningDAO.wijzigSaldoVanKlant(klant, bedrag);
        return rekeningDAO.vindRekeningVanKlant(klant);
    }
}
