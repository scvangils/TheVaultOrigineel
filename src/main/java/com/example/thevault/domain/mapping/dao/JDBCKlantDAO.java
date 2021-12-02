// Created by S.C. van Gils
// Creation date 1-12-2021

package com.example.thevault.domain.mapping.dao;

import com.example.thevault.domain.model.Klant;
import com.example.thevault.support.hashing.HashHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JDBCKlantDAO implements KlantDAO{

    private final Logger logger = LoggerFactory.getLogger(JDBCKlantDAO.class);

    public JDBCKlantDAO() {
        super();
        logger.info("New JDBCKlantDAO");
    }

    @Override
    public void slaKlantOp(Klant klant) {
        List<Klant> klantList = new ArrayList<>();
    klantList.add(klant);
    klant.setGebruikerID(100);
        System.out.println(klantList.contains(klant));
    }

    @Override
    public Klant vindKlantById(int id) {
        return null;
    }

    @Override
    public Klant vindKlantByUsername(String username) {
        String gehashtWachtwoord = HashHelper.hashHelper("testWW");
        Klant testKlant = new Klant(2, "testKlant", gehashtWachtwoord,
                null, null, "Jan", null, 145801354, LocalDate.now());
        if(testKlant.getGebruikersnaam().equals(username)){
            return testKlant;
        }
        return null;
    }
}
