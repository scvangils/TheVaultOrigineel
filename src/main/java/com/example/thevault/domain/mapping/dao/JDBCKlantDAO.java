// Created by S.C. van Gils
// Creation date 1-12-2021

package com.example.thevault.domain.mapping.dao;

import com.example.thevault.domain.model.Klant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
public class JDBCKlantDAO implements KlantDAO{

    private final Logger logger = LoggerFactory.getLogger(JDBCKlantDAO.class);

    private JdbcTemplate jdbcTemplate;

    public JDBCKlantDAO(JdbcTemplate jdbcTemplate) {
        super();
        this.jdbcTemplate = jdbcTemplate;
        logger.info("New JDBCKlantDAO");
    }

    private PreparedStatement slaKlantOpStatement(Klant klant, Connection connection) throws SQLException {
        String sql = "INSERT INTO klant (gebruikersnaam, wachtwoord, naam, bsn, geboortedatum) values (?, ?, ?, ?, ?);";
        PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, klant.getGebruikersnaam());
        ps.setString(2, klant.getWachtwoord());
        ps.setString(3, klant.getNaam());
        ps.setLong(4, klant.getBsn());
        ps.setDate(5, Date.valueOf(klant.getGeboortedatum()));
        return ps;
    }

    @Override
    public void slaKlantOp(Klant klant) {
/*        List<Klant> klantList = new ArrayList<>();
    klantList.add(klant);
    klant.setGebruikerID(100);
        System.out.println(klantList.contains(klant));*/
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> slaKlantOpStatement(klant, connection), keyHolder);
        int gebruikerId = keyHolder.getKey().intValue();
        klant.setGebruikerId(gebruikerId);
    }
    @Override
    public List<Klant> vindAlleKlanten() {
        String sql = "Select * from Klant;";
        return jdbcTemplate.query(sql, new KlantRowMapper());
    }

    @Override
    public Klant vindKlantById(int gebruikerId) {
        String sql = "SELECT * FROM Klant WHERE gebruikerId = ?;";
        return jdbcTemplate.queryForObject(sql, new KlantRowMapper(), gebruikerId);

    }

    @Override
    public Klant vindKlantByGebruikersnaam(String gebruikersnaam) {
/*        String gehashtWachtwoord = HashHelper.hashHelper("testWW");
        Klant testKlant = new Klant(2, "testKlant", gehashtWachtwoord,
                null, null, "Jan", null, 145801354, LocalDate.now());
        if(testKlant.getGebruikersnaam().equals(username)){
            return testKlant;
        }
        return null;*/
        String sql = "SELECT * FROM klant WHERE gebruikersnaam = ?;";
        List<Klant> klantAlsList = jdbcTemplate.query(sql, new KlantRowMapper(), gebruikersnaam);
        if (klantAlsList.size() != 1) return null; // TODO database moet Unique afdwingen
        return klantAlsList.get(0);
    }

    private static class KlantRowMapper implements RowMapper<Klant>{

        @Override //TODO nakijken of datum zo goed geconverteerd wordt
        public Klant mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            return new Klant(resultSet.getInt("gebruikerId"), resultSet.getString("gebruikersnaam"),
                    resultSet.getString("wachtwoord"), resultSet.getString("naam"),
                    resultSet.getLong("bsn"),resultSet.getDate("geboortedatum").toLocalDate());
        }
    }


}
