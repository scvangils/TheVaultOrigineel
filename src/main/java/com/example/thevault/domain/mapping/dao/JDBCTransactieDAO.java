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
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class JDBCTransactieDAO implements TransactieDAO {
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
    public List<Transactie> geefTransactiesVanKlantInPeriode(Klant klant, OffsetDateTime startDatum, OffsetDateTime eindDatum) {
        return null;
    }

    @Override
    public List<Transactie> geefAlleTransactiesInPeriode(OffsetDateTime startDatum, OffsetDateTime eindDatum) {
        return null;
    }

    @Override
    public List<Transactie> geefTransactiesVanKlantMetCryptomunt(Klant klant, Cryptomunt cryptomunt) {
        return null;
    }


    private class TransactieRowMapper implements RowMapper<Transactie> {
        @Override
        public Transactie mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
            OffsetDateTime offsetDateTime = resultSet.getObject("momentTransactie", OffsetDateTime.class);

            Klant koper = new Klant();
            Klant verkoper = new Klant();
            Cryptomunt cryptomunt = new Cryptomunt(resultSet.getInt("cryptomuntId"));
            koper.setGebruikerId(resultSet.getInt("koperGebruikerId"));
            verkoper.setGebruikerId(resultSet.getInt("verkoperGebruikerId"));

            Transactie transactie = new Transactie(offsetDateTime
                    , verkoper, cryptomunt, resultSet.getDouble("bedrag")
                    , resultSet.getDouble("aantal"), koper);
            transactie.setTransactieId(resultSet.getInt("transactieId"));
            return transactie;
        }
    }
}
