// Created by S.C. van Gils
// Creation date 2-12-2021

package com.example.thevault.domain.mapping.dao;

import com.example.thevault.domain.model.Klant;
import com.example.thevault.support.hashing.HashHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class KlantListDatabase {

    private final Logger logger = LoggerFactory.getLogger(KlantListDatabase.class);

    private List<Klant> listDatabase;

    public KlantListDatabase() {
        super();
        this.listDatabase = new ArrayList<>();
        String gehashtWachtwoord = HashHelper.hashHelper("testWW");
        Klant testKlant = new Klant("testKlant", gehashtWachtwoord,
                null, null, "Jan", null, 145801354, LocalDate.now());
        logger.info("New KlantListDatabase");
    }

    public List<Klant> getListDatabase() {
        return listDatabase;
    }

    public void setListDatabase(List<Klant> listDatabase) {
        this.listDatabase = listDatabase;
    }
}
