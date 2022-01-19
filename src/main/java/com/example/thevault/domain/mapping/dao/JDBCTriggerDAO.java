// Created by S.C. van Gils
// Creation date 17-1-2022

package com.example.thevault.domain.mapping.dao;

import com.example.thevault.domain.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Repository
public class JDBCTriggerDAO implements TriggerDAO {

    private final JdbcTemplate jdbcTemplate;

    private final Logger logger = LoggerFactory.getLogger(JDBCTriggerDAO.class);

    //TODO JavaDoc
    public JDBCTriggerDAO(JdbcTemplate jdbcTemplate) {
        super();
        this.jdbcTemplate = jdbcTemplate;
        logger.info("New JDBCTriggerDAO");
    }

    //TODO JavaDoc
    public boolean checkTriggerKoper(Trigger trigger){
        return (trigger instanceof TriggerKoper);
    }

    //TODO JavaDoc
    public String toonJuisteTabel(Trigger trigger){
        if(checkTriggerKoper(trigger)){
            return "triggerKoper";
        }
        return "triggerVerkoper";
    }


    private PreparedStatement slaTriggerOpStatement(Trigger trigger, Connection connection) throws SQLException {
        String sql = String.format("INSERT INTO %s (gebruikerId, cryptoMuntId," +
                " triggerPrijs, aantal, datum) values (?, ?, ?, ?, ?);", toonJuisteTabel(trigger));
        PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, trigger.getGebruiker().getGebruikerId());
        ps.setInt(2, trigger.getCryptomunt().getId());
        ps.setDouble(3, trigger.getTriggerPrijs());
        ps.setDouble(4, trigger.getAantal());
        ps.setDate(5, Date.valueOf(LocalDate.now()));
        return ps;
    }

    /**
     * Deze methode slaat een trigger op in de database met de huidige datum
     * en voegt de door de database gegenereerde id toe aan de trigger
     * Afhankelijk van het type trigger wordt hij in de triggerKoper- of
     * in de triggerVerkopertabel opgeslagen.
     *
     * @param trigger  de betreffende trigger
     * @return de trigger met de gegenereerde id
     */
    @Override
    public Trigger slaTriggerOp(Trigger trigger) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> slaTriggerOpStatement(trigger, connection), keyHolder);
        int triggerId = Objects.requireNonNull(keyHolder.getKey()).intValue();
        trigger.setTriggerId(triggerId);
        return trigger;
    }

    /**
     * Deze methode verwijdert een trigger op basis van zijn id.
     *
     * @param trigger de te verwijderen trigger
     * @return een 0 indien gefaald of niet gevonden, een 1 indien geslaagd
     */
    @Override
    public int verwijderTrigger(Trigger trigger) {
        String sql = String.format("DELETE FROM %s WHERE triggerId = ?;", toonJuisteTabel(trigger));
        int affectedRows = 0;
        try{
            affectedRows = jdbcTemplate.update(sql, trigger.getTriggerId());
        }
        catch(DataAccessException noData){
            logger.warn("het is niet goed gegaan: " + noData.getMessage());
        }
        return affectedRows;
    }

    /** Deze methode zoekt voor een triggerKoper in de triggerVerkoperTabel een match
     * om een transactie mee aan te gaan.
     * Gegeven meerdere matches, eerste het grootste verschil tussen vraag en aanbod,
     * dan de langst staande trigger.
     *
     * @param trigger de betreffende trigger
     * @return de meest geschikte match of null indien geen match
     */
    @Override
    public Trigger vindMatch(Trigger trigger){
        String sql = getMatchSql(trigger);
        try {
            return jdbcTemplate.queryForObject(sql, maakOmgekeerdeRowMapper(trigger),
                    trigger.getAantal(), trigger.getCryptomunt().getId(), trigger.getTriggerPrijs(), trigger.getGebruiker().getGebruikerId());
        }
        catch (
                EmptyResultDataAccessException exception){
            logger.warn("Geen data gevonden, exceptie: " + exception);
        }
        return null;
    }

    private String getMatchSql(Trigger trigger) {
        String koperSql = "SELECT * FROM triggerKoper WHERE aantal = ? AND cryptomuntId = ? AND triggerPrijs >= ? " +
                "AND NOT gebruikerId = ? ORDER BY triggerPrijs DESC, datum ASC LIMIT 1;";
        String verkoperSql = "SELECT * FROM triggerVerkoper WHERE aantal = ? AND cryptomuntId = ? AND triggerPrijs <= ? " +
                "AND NOT gebruikerId = ? ORDER BY triggerPrijs ASC, datum ASC LIMIT 1;";
        return (checkTriggerKoper(trigger)) ? verkoperSql: koperSql;
    }

    //TODO JavaDoc
    @Override
    public List<Trigger> vindTriggersByGebruiker(Gebruiker gebruiker, String koperOfVerkoper) {
        String tabel = "trigger" + koperOfVerkoper;
        String sql = String.format("SELECT * FROM %s WHERE gebruikerId = ?;", tabel);
        try{
            return jdbcTemplate.query(sql, maakJuisteRowMapper(koperOfVerkoper), gebruiker.getGebruikerId());
        }
        catch (
                EmptyResultDataAccessException exception){
            logger.warn("Geen data gevonden, exceptie: " + exception);
        }
        return null;
    }

    /**
     * Geeft alle triggers van een bepaald type aanwezig in de database
     *
     * @param koperOfVerkoper Geeft aan welke tabel gebruikt moet worden
     * @return een List van Triggers, geheel bestaand uit een enkele subklasse
     */
    @Override
    public List<Trigger> vindAlleTriggers(String koperOfVerkoper) {
        String tabel = "trigger" + koperOfVerkoper;
        String sql = String.format("SELECT * FROM %s;", tabel);
        try {
            return jdbcTemplate.query(sql, maakJuisteRowMapper(koperOfVerkoper));
        }
        catch (
                EmptyResultDataAccessException exception){
            logger.warn("Geen data gevonden, exceptie: " + exception);
        }
        return null;
    }

    //TODO JavaDoc
    public static Trigger getTrigger(ResultSet resultSet, Trigger trigger) throws SQLException {
        trigger.setTriggerId(resultSet.getInt("triggerId"));
        trigger.setTriggerPrijs(resultSet.getDouble("triggerPrijs"));
        trigger.setAantal(resultSet.getDouble("aantal"));
        trigger.setDatum(resultSet.getDate("datum").toLocalDate());
        Gebruiker gebruiker = new Klant();
        gebruiker.setGebruikerId(resultSet.getInt("gebruikerId"));
        trigger.setGebruiker(gebruiker);
        Cryptomunt cryptomunt = new Cryptomunt(resultSet.getInt("cryptomuntId"));
        trigger.setCryptomunt(cryptomunt);
        return trigger;
    }

    //TODO JavaDoc
    public RowMapper<Trigger> maakJuisteRowMapper(Trigger trigger){
        if(checkTriggerKoper(trigger)){
            return new TriggerKoperRowMapper();
        }
        else return new TriggerVerkoperRowMapper();
    }

    //TODO JavaDoc
    public RowMapper<Trigger> maakJuisteRowMapper(String typeGebruiker){
        if(typeGebruiker.equals("Koper")){
            return new TriggerKoperRowMapper();
        }
        else return new TriggerVerkoperRowMapper();
    }

    //TODO JavaDoc
    public RowMapper<Trigger> maakOmgekeerdeRowMapper(Trigger trigger) {
        if (!checkTriggerKoper(trigger)) {
            return new TriggerKoperRowMapper();
        } else return new TriggerVerkoperRowMapper();
    }

    private static class TriggerVerkoperRowMapper implements RowMapper<Trigger> {

        @Override
        public Trigger mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            Trigger trigger = new TriggerVerkoper();
            return getTrigger(resultSet, trigger);
        }
    }

    private static class TriggerKoperRowMapper implements RowMapper<Trigger> {

        @Override
        public Trigger mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            Trigger trigger = new TriggerKoper();
            return getTrigger(resultSet, trigger);
        }
    }

}
