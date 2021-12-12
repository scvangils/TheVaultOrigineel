// Created by carme
// Creation date 11/12/2021

package com.example.thevault.domain.mapping.dao;

import com.example.thevault.domain.model.Asset;
import com.example.thevault.domain.model.Cryptomunt;
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

@Repository
public class JDBCCryptomuntDAO implements CryptomuntDAO{

    private JdbcTemplate jdbcTemplate;

    @JsonIgnore
    private final Logger logger = LoggerFactory.getLogger(JDBCCryptomuntDAO.class);

    @Autowired
    public JDBCCryptomuntDAO(JdbcTemplate jdbcTemplate) {
        super();
        logger.info("New JDBCCryptomuntDAO");
    }

    private static class CryptomuntRowMapper implements RowMapper<Cryptomunt> {
        @Override
        public Cryptomunt mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            return new Cryptomunt(resultSet.getInt("cryptomuntId"), resultSet.getString("naam"),
                    resultSet.getString("afkorting"), resultSet.getDouble("waarde"));
        }
    }

    /**
     * Methode die de informatie over een specifieke cryptomunt uit de database haalt
     * @param cryptomuntId cryptomuntidentifier waarover informatie wordt opgevraagd
     * @return Cryptomunt alle informatie over de opgevraagde cryptomunt
     */
    @Override
    public Cryptomunt geefCryptomunt(int cryptomuntId) {
        String sql = "Select * from cryptomunt where cryptomuntId = ?;";
        return jdbcTemplate.queryForObject(sql, new JDBCCryptomuntDAO.CryptomuntRowMapper(), cryptomuntId);
    }
}
