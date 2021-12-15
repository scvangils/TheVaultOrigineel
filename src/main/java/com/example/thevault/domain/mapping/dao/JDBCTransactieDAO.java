// Created by E.S. Olthof
// Creation date 12/15/2021

package com.example.thevault.domain.mapping.dao;

import com.example.thevault.domain.model.Bank;
import com.example.thevault.domain.model.Cryptomunt;
import com.example.thevault.domain.model.Klant;
import com.example.thevault.domain.model.Transactie;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class JDBCTransactieDAO implements TransactieDAO{
    JdbcTemplate jdbcTemplate;

    @JsonIgnore
    private final Logger logger = LoggerFactory.getLogger(JDBCKlantDAO.class);

    @Autowired
    public JDBCTransactieDAO(JdbcTemplate jdbcTemplate) {
        super();
        this.jdbcTemplate = jdbcTemplate;
        logger.info("New JDBCTransactieDAO");
    }


    @Override
    public Transactie slaTransactieOp(Transactie transactie) {
        return null;
    }


    @Override
    public List<Transactie> geefTransactiesVanKlant(Klant klant) {
        return null;
    }

    @Override
    public List<Transactie> geefTransactiesVanBank() {
        return null;
    }

    @Override
    public List<Transactie> geefTransactiesVanKlantInPeriode(Klant klant, LocalDateTime startDatum, LocalDateTime eindDatum) {
        return null;
    }

    @Override
    public List<Transactie> geefAlleTransactiesInPeriode(LocalDateTime startDatum, LocalDateTime eindDatum) {
        return null;
    }

    @Override
    public List<Transactie> geefTransactiesVanKlantMetCryptomunt(Klant klant, Cryptomunt cryptomunt) {
        return null;
    }

}
