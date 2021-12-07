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

    /**
     * Deze methode is nodig om een klant met een auto-increment id op te slaan
     * in de database.
     *
     * @param klant het klant object met de gegevens die moeten worden opgeslagen
     * @param connection het Connection-object nodig om de Prepared Statement aan te maken
     * @return Prepared Statement-object dat nodig is om Klant op te slaan in de database
     * @throws SQLException
     */
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


    /**
     * Deze methode slaat de gegevens van een klant op in de database
     * op basis van een klant-object en zet de door auto-increment gegenereerd
     * gebruikerId in het klant-object
     *
     * @param klant het klant-object op basis van bij registratie ingevoerde gegevens
     * @return het klant-object met de juiste gebruikerId
     */
    @Override
    public Klant slaKlantOp(Klant klant) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> slaKlantOpStatement(klant, connection), keyHolder);
        int gebruikerId = keyHolder.getKey().intValue();
        klant.setGebruikerId(gebruikerId);
        return klant;
    }

    /**
     * Deze methode zoekt alle klanten in de database op en maakt een List van Klant-objecten aan op basis
     * van de teruggestuurde gegevens
     *
     * @return een List van klant-objecten
     */
    @Override
    public List<Klant> vindAlleKlanten() {
        String sql = "Select * from Klant;";
        return jdbcTemplate.query(sql, new KlantRowMapper());
    }

    /**
     * Deze methode zoekt of er in de database al een klant bestaat met deze gebruikerId
     * en maakt eventueel een klant-object aan op nasis van de teruggestuurde gegevens
     *
     * @param gebruikerId gebruikerId van een (mogelijke) klant die uniek moet zijn
     * @return klant-object op basis van gegevens uit de database of null indien gebruikerId niet gevonden is
     */
    @Override
    public Klant vindKlantById(int gebruikerId) {
        String sql = "SELECT * FROM Klant WHERE gebruikerId = ?;";
        return jdbcTemplate.queryForObject(sql, new KlantRowMapper(), gebruikerId);

    }

    /**
     * Deze methode zoekt of er in de database al een klant bestaat met deze gebruikersnaam
     * en maakt eventueel een klant-object aan op nasis van de teruggestuurde gegevens
     *
     * @param gebruikersnaam gebruikersnaam van een (mogelijke) klant die uniek moet zijn
     * @return klant-object op basis van gegevens uit de database of null indien gebruikersnaam niet gevonden is
     */
    @Override
    public Klant vindKlantByGebruikersnaam(String gebruikersnaam) {
        String sql = "SELECT * FROM klant WHERE gebruikersnaam = ?;";
        return jdbcTemplate.queryForObject(sql, new KlantRowMapper(), gebruikersnaam);
    }

    private static class KlantRowMapper implements RowMapper<Klant>{
        /**
         * Deze methode is, samen met een sql-query, nodig om in
         * het Spring framework eenvoudig een of meerdere Klant-objecten op basis van
         * de database aan te maken. De gebruikerId wordt via de setter gezet.
         *
         * @param resultSet resultaat van query in Klant-tabel database
         * @param rowNum getal nodig voor iteratie over ResultSet
         * @return Klant-object met informatie uit de database
         * @throws SQLException
         */
        @Override //TODO nakijken of datum zo goed geconverteerd wordt
        public Klant mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            Klant klant = new Klant(resultSet.getString("gebruikersnaam"),
                    resultSet.getString("wachtwoord"), resultSet.getString("naam"),
                    resultSet.getLong("bsn"),resultSet.getDate("geboortedatum").toLocalDate());
            klant.setGebruikerId(resultSet.getInt("gebruikerId"));
            return klant;
        }
    }


}
