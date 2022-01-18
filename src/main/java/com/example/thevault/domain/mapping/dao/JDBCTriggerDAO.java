// Created by S.C. van Gils
// Creation date 17-1-2022

package com.example.thevault.domain.mapping.dao;

import com.example.thevault.domain.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.*;
import java.util.List;
import java.util.Objects;

public class JDBCTriggerDAO implements TriggerDAO {

    private final JdbcTemplate jdbcTemplate;

    private final Logger logger = LoggerFactory.getLogger(JDBCTriggerDAO.class);

    public JDBCTriggerDAO(JdbcTemplate jdbcTemplate) {
        super();
        this.jdbcTemplate = jdbcTemplate;
        logger.info("New JDBCTriggerDAO");
    }

    public boolean checkTriggerKoper(Trigger trigger){
        return (trigger instanceof TriggerKoper);
    }

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
        ps.setDate(5, Date.valueOf(trigger.getDatum()));
        return ps;
    }

    @Override
    public Trigger slaTriggerOp(Trigger trigger) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> slaTriggerOpStatement(trigger, connection), keyHolder);
        int triggerId = Objects.requireNonNull(keyHolder.getKey()).intValue();
        trigger.setTriggerId(triggerId);
        return trigger;
    }

    @Override
    public int verwijderTrigger(Trigger trigger) {
        String sql = String.format("DELETE FROM %s WHERE triggerId = ?;", toonJuisteTabel(trigger));

        return 0;
    }

    // TODO omkering koperOfVerkoper regelen
    @Override
    public List<Trigger> vindTriggersByGebruiker(Gebruiker gebruiker, String koperOfVerkoper) {
        String tabel = "trigger" + koperOfVerkoper;
        String sql = String.format("SELECT * FROM %s WHERE gebruikerId = ?;", tabel);
        try{
            return jdbcTemplate.query(sql, maakJuisteRowMapper(koperOfVerkoper), gebruiker.getGebruikerId());
        }
    catch (
    EmptyResultDataAccessException exception){
        System.out.println("Geen data gevonden, exceptie: " + exception);
    }
        return null;
    }

    @Override
    public List<Trigger> vindAlleTriggers(String koperOfVerkoper) {
        String tabel = "trigger" + koperOfVerkoper;
        String sql = String.format("SELECT * FROM %s;", tabel);
        return null;
    }

    // hier checken dat je niet met jezelf handelt?
    // zou vreemd zijn wellicht
    // checken bij opslaan?
    // TODO omkering koperOfVerkoper regelen
    @Override
    public List<Trigger> vindTriggersByAantalCryptomuntEnPrijs(Trigger trigger) {
        String sql = String.format("SELECT * FROM %s WHERE aantal = ? en cryptomuntId = ?", toonJuisteTabel(trigger));
        return null;
    }

    public static Trigger getTrigger(ResultSet resultSet, Trigger trigger) throws SQLException {
        trigger.setTriggerId(resultSet.getInt("triggerId"));
        trigger.setTriggerPrijs(resultSet.getDouble("triggerPrijs"));
        trigger.setAantal(resultSet.getDouble("aantal"));
        trigger.setDatum(resultSet.getDate("datum").toLocalDate());
        Gebruiker gebruiker = new Klant();
        gebruiker.setGebruikerId(resultSet.getInt("koperGebruikerId"));
        trigger.setGebruiker(gebruiker);
        Cryptomunt cryptomunt = new Cryptomunt(resultSet.getInt("cryptomuntId"));
        trigger.setCryptomunt(cryptomunt);
        return trigger;
    }

    public RowMapper<Trigger> maakJuisteRowMapper(Trigger trigger){
        if(checkTriggerKoper(trigger)){
            return new TriggerKoperRowMapper();
        }
        else return new TriggerVerkoperRowMapper();
    }
    public RowMapper<Trigger> maakJuisteRowMapper(String typeGebruiker){
        if(typeGebruiker.equals("Koper")){
            return new TriggerKoperRowMapper();
        }
        else return new TriggerVerkoperRowMapper();
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
