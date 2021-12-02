// Created by S.C. van Gils
// Creation date 1-12-2021

package com.example.thevault.domain.mapping.dao;

import com.example.thevault.domain.model.Klant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class JDBCKlantDAO implements KlantDAO{

    private final Logger logger = LoggerFactory.getLogger(JDBCKlantDAO.class);

    private KlantListDatabase klantDatabase;

    public JDBCKlantDAO() {
        super();
        logger.info("New JDBCKlantDAO");
    }

    @Override
    public void slaKlantOp(Klant klant) {

    }

    @Override
    public Klant vindKlantById(int id) {
        return null;
    }

    @Override
    public Klant vindKlantByUsername(String username) {
        return null;
    }
}
