// Created by carme
// Creation date 03/12/2021

package com.example.thevault.domain.mapping.dao;

import com.example.thevault.domain.model.Asset;
import com.example.thevault.domain.model.Cryptomunt;
import com.example.thevault.domain.model.Klant;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Carmen Rietdijk
 * Beschrijving: De implementatie van de DAO voor Asset, waarin zowel de CRUD functionaliteit van Asset wordt geregeld,
 * alsmede het ophalen van de benodigde informatie voor het vullen van de portefeuille van de klant
 */

@Repository
public class JDBCAssetDAO implements AssetDAO{

    @JsonIgnore
    private final Logger logger = LoggerFactory.getLogger(JDBCAssetDAO.class);

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JDBCAssetDAO(JdbcTemplate jdbcTemplate) {
        super();
        this.jdbcTemplate = jdbcTemplate;
        logger.info("New JDBCAssetDAO");
    }

    private PreparedStatement slaAssetOpStatement(Asset asset, Connection connection) throws SQLException {
        String sql = "INSERT INTO asset (gebruikerId, cryptomuntId, aantal, datum) values " +
                "(?, ?, ?, ?, ?, ?);";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, asset.getKlant().getGebruikerId());
        ps.setInt(2, asset.getCryptomunt().getId());
        ps.setDouble(3, asset.getAantal());
        ps.setString(4, asset.getDatum().toString());
        return ps;
    }

    private PreparedStatement verwijderAssetStatement(Asset asset, Connection connection) throws SQLException {
        String sql = "DELETE * FROM asset WHERE gebruikerId = ? AND cryptomuntId = ?;";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, asset.getKlant().getGebruikerId());
        ps.setInt(2, asset.getCryptomunt().getId());
        return ps;
    }

    private static class AssetRowMapper implements RowMapper<Asset> {
        @Override
        public Asset mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            Cryptomunt cryptomunt = new Cryptomunt(resultSet.getInt("cryptomuntId"));
            return new Asset(cryptomunt, resultSet.getDouble("aantal"),
                    LocalDateTime.parse(resultSet.getString("datum")));
        }
    }

    /**
     * Dit betreft het toevoegen van een cryptomunt die nog niet in de portefeuille zit
     * Dit gebeurt via een 'transactie', waarbij een klant crypto's koopt
     * @param asset de cryptomunt en het aantal dat de klant aanschaft
     * @return Asset de asset die de klant heeft toegevoegd
     */
    @Override
    public Asset voegNieuwAssetToeAanPortefeuille(Asset asset) {
        jdbcTemplate.update(connection -> slaAssetOpStatement(asset, connection));
        return asset;
    }

    //TODO Besluiten of deze methode nodig is. Eigenlijk is 'verwijderen' een speciale situatie van 'updaten' waarbij de
    // hoeveelheid verkochte cryptomunt gelijk is aan de hoeveelheid aanwezig in de portefeuille.
    /**
     * Dit betreft het verwijderen van een cryptomunt uit de portefeuille
     * Dit gebeurt via een 'transactie', waarbij een klant crypto's verkoopt
     * @param asset de asset die uit de portefeuille wordt verwijderd
     * @return String bericht dat de cryptomunt uit de portefeuille is verwijderd
     */
    @Override
    public Asset verwijderAssetUitPortefeuille(Asset asset) {
        jdbcTemplate.update(connection -> verwijderAssetStatement(asset, connection));
        asset.setAantal(0);
        return asset;
    }

    //TODO Besluiten of dit twee methodes moeten worden: kopen vs verkopen. Op zich gebeurt er in beide gevallen
    // hetzelfde, namelijk het aanpassen van het aantal van de cryptomunt.
    /**
     * Dit betreft het updaten van een cryptomunt die al in de portefeuille zit
     * Dit gebeurt via een 'transactie', waarbij een klant crypto's koopt of verkoopt
     * @param asset de asset waarin de klant handelt, met de informatie wélke cryptomunt wordt verhandeld
     *              en hoeveel deze omhoog/omlaag gaat (oftewel: betreft het een koop of een verkoop)
     * @return Asset de asset na de update, waarbij het nieuwe aantal wordt meegegeven
     */
    @Override
    public Asset updateAsset(Asset asset) {
        double huidigeAantal = geefAsset(asset.getKlant(), asset.getCryptomunt()).getAantal();
        double teVerhandelenAantal = asset.getAantal();
        if(huidigeAantal >= teVerhandelenAantal) {
            Asset nieuwAsset = new Asset(asset.getCryptomunt(), huidigeAantal - teVerhandelenAantal,
                    asset.getKlant(), asset.getDatum());
            jdbcTemplate.update(connection -> verwijderAssetStatement(asset, connection));
            jdbcTemplate.update(connection -> slaAssetOpStatement(nieuwAsset, connection));
            return nieuwAsset;
        } else if(huidigeAantal == -teVerhandelenAantal){
            return verwijderAssetUitPortefeuille(asset);
        }
        System.out.println("Het saldo van deze cryptomunt is te laag voor deze transactie");
        return null;
    }

    /**
     * Dit betreft het vinden van een cryptomunt die in de portefeuille zit
     * @param klant klant die informatie opvraagt over de cryptomunt
     * @param cryptomunt cryptomunt waarover informatie wordt opgevraagd
     * @return Asset de asset (cryptomunt + aantal) waarover informatie is opgevraagd
     */
    @Override
    public Asset geefAsset(Klant klant, Cryptomunt cryptomunt){
        String sql = "Select * from asset where gebruikerId = ? AND cryptomuntId = ?;";
        Asset asset = jdbcTemplate.queryForObject(sql, new JDBCAssetDAO.AssetRowMapper(), klant.getGebruikerId(),
                cryptomunt.getId());
        asset.setKlant(klant);
        return asset;
    }

    /**
     * Dit betreft het vinden van alle cryptomunten die in de portefeuille zitten
     * @param klant klant die informatie opvraagt over de cryptomunt
     * @return List</Asset> een lijst van alle Assets (cryptomunten + hoeveelheden) in het bezit van de klant
     */
    @Override
    public List<Asset> geefAlleAssets(Klant klant){
        String sql = "SELECT * FROM asset WHERE gebruikerId = ?;";
        List<Asset> assets = jdbcTemplate.query(sql, new JDBCAssetDAO.AssetRowMapper(), klant.getGebruikerId());
        for (Asset asset : assets) {
            asset.setKlant(klant);
        }
        return assets;
    }

}
