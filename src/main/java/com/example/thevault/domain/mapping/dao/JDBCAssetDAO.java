// Created by carme
// Creation date 03/12/2021

package com.example.thevault.domain.mapping.dao;

import com.example.thevault.domain.model.Asset;
import com.example.thevault.domain.model.Cryptomunt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

/**
 * @Author: Carmen Rietdijk
 * Beschrijving: De implementatie van de DAO voor Asset, waarin zowel de CRUD functionaliteit van Asset wordt geregeld,
 * alsmede het ophalen van de benodigde informatie voor het vullen van de portefeuille van de klant
 */

@Repository
public class JDBCAssetDAO implements AssetDAO{

    private final Logger logger = LoggerFactory.getLogger(JDBCAssetDAO.class);

    private JdbcTemplate jdbcTemplate;

    public JDBCAssetDAO(JdbcTemplate jdbcTemplate) {
        super();
        this.jdbcTemplate = jdbcTemplate;
        logger.info("New JDBCAssetDAO");
    }

    private PreparedStatement slaAssetOpStatement(int klantId, Asset asset, Connection connection) throws SQLException {
        String sql = "INSERT INTO asset (klantId, cryptomuntId, naam, afkorting, waarde, aantal) values " +
                "(?, ?, ?, ?, ?, ?);";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, klantId);
        ps.setInt(2, asset.getCryptomunt().getId());
        ps.setString(3, asset.getCryptomunt().getName());
        ps.setString(4, asset.getCryptomunt().getSymbol());
        ps.setDouble(5, asset.getCryptomunt().getPrice());
        ps.setDouble(6, asset.getAantal());
        return ps;
    }

    private PreparedStatement geefAlleAssetsStatement(int klantId, Connection connection) throws SQLException {
        String sql = "SELECT * FROM asset WHERE klantId = ?;";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, klantId);
        return ps;
    }

    private static class AssetRowMapper implements RowMapper<Asset> {
        @Override
        public Asset mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            Cryptomunt cryptomunt = new Cryptomunt(resultSet.getInt("cryptomuntId"), resultSet.getString("naam"),
                    resultSet.getString("afkorting"), resultSet.getDouble("waarde"));
            return new Asset(cryptomunt, resultSet.getDouble("aantal"));
        }
    }

    /**
     * Dit betreft het toevoegen van een cryptomunt die nog niet in de portefeuille zit
     * Dit gebeurt via een 'transactie', waarbij een klant crypto's koopt
     * @param klantId identifier van de klant die de cryptomunt koopt
     * @param asset de cryptomunt en het aantal dat de klant aanschaft
     * @return Asset de asset die de klant heeft toegevoegd
     */
    @Override
    public Asset voegNieuwAssetToeAanPortefeuille(int klantId, Asset asset) {
        jdbcTemplate.update(connection -> slaAssetOpStatement(klantId, asset, connection));
        return asset;
    }

    //TODO Besluiten of deze methode nodig is. Eigenlijk is 'verwijderen' een speciale situatie van 'updaten' waarbij de
    // hoeveelheid verkochte cryptomunt gelijk is aan de hoeveelheid aanwezig in de portefeuille.
    /**
     * Dit betreft het verwijderen van een cryptomunt uit de portefeuille
     * Dit gebeurt via een 'transactie', waarbij een klant crypto's verkoopt
     * @param klantId identifier van de klant die de cryptomunt verkoopt
     * @param cryptomuntId de identifier van de cryptomunt die uit de portefeuille wordt verwijderd
     * @return String bericht dat de cryptomunt uit de portefeuille is verwijderd
     */
    @Override
    public String verwijderAssetUitPortefeuille(int klantId, int cryptomuntId) {
        return "Cryptomunt is verwijderd";
    }

    //TODO Besluiten of dit twee methodes moeten worden: kopen vs verkopen. Op zich gebeurt er in beide gevallen
    // hetzelfde, namelijk het aanpassen van het aantal van de cryptomunt.
    /**
     * Dit betreft het updaten van een cryptomunt die al in de portefeuille zit
     * Dit gebeurt via een 'transactie', waarbij een klant crypto's koopt of verkoopt
     * @param klantId identifier van de klant die de cryptomunt koopt/verkoopt
     * @param asset de asset waarin de klant handelt, met de informatie wélke cryptomunt wordt verhandeld
     *              en hoeveel deze omhoog/omlaag gaat (oftewel: betreft het een koop of een verkoop)
     * @return Asset de asset na de update, waarbij het nieuwe aantal wordt meegegeven
     */
    @Override
    public Asset updateAsset(int klantId, Asset asset) {
        double huidigeAantal = geefAsset(klantId, asset.getCryptomunt().getId()).getAantal();
        double teVerhandelenAantal = asset.getAantal();
        if(huidigeAantal >= teVerhandelenAantal) {
            Asset nieuwAsset = new Asset(asset.getCryptomunt(), huidigeAantal - teVerhandelenAantal);
            jdbcTemplate.update(connection -> slaAssetOpStatement(klantId, nieuwAsset, connection));
            return nieuwAsset;
        }
        System.out.println("Het saldo van deze cryptomunt is te laag voor deze transactie");
        return null;
    }

    /**
     * Dit betreft het vinden van een cryptomunt die in de portefeuille zit
     * @param klantId identifier van de klant die informatie opvraagt over de cryptomunt
     * @param cryptomuntId identifier waarover informatie wordt opgevraagd
     * @return Asset de asset (cryptomunt + aantal) waarover informatie is opgevraagd
     */
    @Override
    public Asset geefAsset(int klantId, int cryptomuntId){
        String sql = "Select * from asset where klantId = ? AND cryptomuntId = ?;";
        return jdbcTemplate.queryForObject(sql, new JDBCAssetDAO.AssetRowMapper(), klantId, cryptomuntId);
    }

    /**
     * Dit betreft het vinden van alle cryptomunten die in de portefeuille zitten
     * @param klantId identifier van de klant die informatie opvraagt over de cryptomunt
     * @return List</Asset> een lijst van alle Assets (cryptomunten + hoeveelheden) in het bezit van de klant
     */
    @Override
    public List<Asset> geefAlleAssets(int klantId){
        String sql = "SELECT * FROM asset WHERE klantId = ?;";
        return jdbcTemplate.query(sql, new JDBCAssetDAO.AssetRowMapper(), klantId);
    }

}
