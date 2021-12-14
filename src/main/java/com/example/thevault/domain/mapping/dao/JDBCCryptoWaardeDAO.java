// Created by S.C. van Gils
// Creation date 14-12-2021

package com.example.thevault.domain.mapping.dao;

import com.example.thevault.domain.model.CryptoWaarde;
import com.example.thevault.domain.model.Cryptomunt;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class JDBCCryptoWaardeDAO implements CryptoWaardeDAO {

    private final JdbcTemplate jdbcTemplate;

    @JsonIgnore
    private final Logger logger = LoggerFactory.getLogger(JDBCCryptoWaardeDAO.class);

    public JDBCCryptoWaardeDAO(JdbcTemplate jdbcTemplate) {
        super();
        this.jdbcTemplate = jdbcTemplate;
        logger.info("New JDBCCryptoWaardeDAO");
    }

    //voor historische lijst
    //TODO CryptoWaardeDto
    @Override
    public List<CryptoWaarde> getCryptoWaardeByCryptomunt(Cryptomunt cryptomunt) {
        String sql = "SELECT * FROM cryptowaarde WHERE cryptomuntId = ?;";
        jdbcTemplate.query(sql, new CryptoWaardeRowMapper(), cryptomunt.getId());
        return null;
    }
    //vooral handig voor laatste waarde
    @Override
    public CryptoWaarde getCryptoWaardeByCryptomuntAndDate(Cryptomunt cryptomunt, LocalDate datum) {
        CryptoWaarde cryptoWaarde;
        String sql = "SELECT * FROM cryptowaarde WHERE cryptomuntId = ? AND datum = ?;";
        try {
            cryptoWaarde = jdbcTemplate.queryForObject(sql, new CryptoWaardeRowMapper(), cryptomunt.getId(), Date.valueOf(datum));
        }
        catch (EmptyResultDataAccessException geenResultaat){
            cryptoWaarde = null;
        }
        return cryptoWaarde;
    }

    //TODO hier juiste id meegeven
    public CryptoWaarde slaCryptoWaardeOp(CryptoWaarde cryptoWaarde) {
        String sql = "INSERT INTO cryptowaarde VALUES(?, ?, ?, ?);";
        LocalDate huidigeDatum = LocalDate.now();
        cryptoWaarde.setDatum(huidigeDatum);
        cryptoWaarde.setCryptoWaardeId(generateCryptoWaardeId(huidigeDatum, cryptoWaarde.getCryptomunt()));
        jdbcTemplate.update(sql, cryptoWaarde.getCryptoWaardeId(), cryptoWaarde.getCryptomunt().getId(),
                cryptoWaarde.getWaarde(), Date.valueOf(cryptoWaarde.getDatum()));
        return cryptoWaarde;
    }

    private String generateCryptoWaardeId(LocalDate localDate, Cryptomunt cryptomunt) {
        int jaar = localDate.getYear();
        int maand = localDate.getMonthValue();
        int dag = localDate.getDayOfMonth();
        String dagUitTweeCijfers = String.valueOf(dag);
        if (dag < 10) {
            dagUitTweeCijfers = "0" + dag;
        }
        return jaar + maand + dagUitTweeCijfers + cryptomunt.getSymbol();

    }

    private static class CryptoWaardeRowMapper implements RowMapper<CryptoWaarde> {

    @Override
    public CryptoWaarde mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new CryptoWaarde(resultSet.getString("cryptoWaardeId"),
                null,
                resultSet.getDouble("waarde"), resultSet.getDate("datum").toLocalDate());
    }
}
}
