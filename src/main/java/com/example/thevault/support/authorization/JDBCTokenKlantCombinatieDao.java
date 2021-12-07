/*
* @Author Elise Olthof
* 12-07-2021
* */

package com.example.thevault.support.authorization;

import com.example.thevault.domain.mapping.dao.KlantDAO;
import com.example.thevault.domain.model.Klant;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class JDBCTokenKlantCombinatieDao implements TokenKlantCombinatieDao{

    private JdbcTemplate jdbcTemplate;
    private KlantDAO klantDAO;

    @JsonIgnore
    private final Logger logger = LoggerFactory.getLogger(AuthorizationSupport.class);

    @Autowired

    public JDBCTokenKlantCombinatieDao(JdbcTemplate jdbcTemplate, KlantDAO klantDAO) {
        super();
        this.jdbcTemplate = jdbcTemplate;
        this.klantDAO = klantDAO;
        logger.info("New JDBCTokenKlantCombinatieDao.");

    }

    @Override
    public void slaTokenKlantPairOp(TokenKlantCombinatie tokenKlantCombinatie) {

    }

    @Override
    public Optional<TokenKlantCombinatie> vindTokenKlantPairMetKey(UUID key) {
        return Optional.empty();
    }

    @Override
    public Optional<TokenKlantCombinatie> vindTokenKlantPairMetKlant(Klant klant) {
        return Optional.empty();
    }

    @Override
    public void delete(UUID uuid) {

    }
}
