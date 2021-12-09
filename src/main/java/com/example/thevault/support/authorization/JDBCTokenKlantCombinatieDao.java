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
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class JDBCTokenKlantCombinatieDao implements TokenKlantCombinatieDao{

    private JdbcTemplate jdbcTemplate;
    private KlantDAO klantDAO;

    @JsonIgnore
    private final Logger logger = LoggerFactory.getLogger(AuthorizationService.class);

    @Autowired
    public JDBCTokenKlantCombinatieDao(JdbcTemplate jdbcTemplate, KlantDAO klantDAO) {
        super();
        this.jdbcTemplate = jdbcTemplate;
        this.klantDAO = klantDAO;
        logger.info("New JDBCTokenKlantCombinatieDao.");

    }

    @Override
    public void slaTokenKlantPairOp(TokenKlantCombinatie tokenKlantCombinatie) {
        jdbcTemplate.update(
                "insert into connection_table (uuid, klant_fk) values (?, ?)",
                tokenKlantCombinatie.getKey().toString(), tokenKlantCombinatie.getKlant().getGebruikerId());
    }


    @Override
    public Optional<TokenKlantCombinatie> vindTokenKlantPairMetKey(UUID key) {
        List<TokenKlantCombinatie> tokenKlantCombinaties =
                jdbcTemplate.query(
                        "select * from connection_table where uuid = ?", new ConnectionRowMapper(), key.toString());
        if (tokenKlantCombinaties.size() == 1) {
            return Optional.of(tokenKlantCombinaties.get(0));
        }
        return Optional.empty();    }

    @Override
    public Optional<TokenKlantCombinatie> vindTokenKlantCombinatieMetKlant(Klant klant) {
        List<TokenKlantCombinatie> tokenKlantCombinaties =
                jdbcTemplate.query(
                        "select * from connection_table where klant_fk = ?", new ConnectionRowMapper(), klant.getGebruikerId());
        if (tokenKlantCombinaties.size() == 1) {
            return Optional.of(tokenKlantCombinaties.get(0));
        }
        return Optional.empty();
    }

    @Override
    public void delete(UUID uuid) {
        jdbcTemplate.update("delete from connection_table where uuid = ?", uuid.toString());
        logger.info("Verwijderde uuid " + uuid.toString());
    }


    // maak een inner class aan voor een rowmapper
    private class ConnectionRowMapper implements RowMapper<TokenKlantCombinatie> {

        @Override
        public TokenKlantCombinatie mapRow(ResultSet resultSet, int i) throws SQLException {
            UUID uuid = UUID.fromString(resultSet.getString("uuid"));
            int klantId = resultSet.getInt("klant_fk");
            Klant klant = klantDAO.vindKlantById(klantId);
            return new TokenKlantCombinatie(uuid, klant);
        }
    }

}

