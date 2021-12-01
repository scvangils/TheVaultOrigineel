// Created by carme
// Creation date 01/12/2021

package com.example.thevault.domain.mapping.dao;

import com.example.thevault.domain.model.Portefeuille;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MySQLPortefeuilleDAO implements PortefeuilleDAO {

    private final Logger logger = LoggerFactory.getLogger(MySQLPortefeuilleDAO.class);

    public MySQLPortefeuilleDAO() {
        super();
        logger.info("New MySQLPortefeuilleDAO");
    }

    /**
     * Author: Carmen
     * Vraag mbt vullen methodes van DAOs zonder database: dat wordt dubbel werk, want eerst schrijven we ze
     * voor de Map/Set/etc, en daarna worden ze helemaal herschreven voor de database.
     * Beter om nu een database te maken? Hoort niet bij de Sprint. Overleggen met PO?
     */

    @Override
    public Portefeuille vindPortefeuilleInhoudMetID(int klantId) {
        return null;
    }

    @Override
    public void voegCryptomuntToe(int cryptomuntId) {

    }

    @Override
    public void verwijderCryptomunt(int cryptomuntId) {

    }

    /**
     * @Author Carmen
     * Deze methode heeft een link met 'transactie', een object/tabel wat we nog moeten bedenken en uitwerken
     */
    @Override
    public void updateSaldoCryptomunt(int cryptomuntId, int hoeveelheid) {

    }
}
