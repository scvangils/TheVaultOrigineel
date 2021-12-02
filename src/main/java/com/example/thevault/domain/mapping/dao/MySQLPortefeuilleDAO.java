// Created by carme
// Creation date 01/12/2021

package com.example.thevault.domain.mapping.dao;

import com.example.thevault.domain.model.Portefeuille;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class MySQLPortefeuilleDAO implements PortefeuilleDAO {

    private JdbcTemplate jdbcTemplate; //Is deze nodig?
    private final Logger logger = LoggerFactory.getLogger(MySQLPortefeuilleDAO.class);

    //Klopt de injectie van jdbcTemplate zo?
    @Autowired
    public MySQLPortefeuilleDAO(JdbcTemplate jdbcTemplate) {
        super();
        this.jdbcTemplate = jdbcTemplate;
        logger.info("New MySQLPortefeuilleDAO");
    }

    /**
     * @Author Carmen
     * Dit wordt een method om PreparedStatements aan te maken
     * Als 'aantal' de hoeveelheid van de cryptomunt is in de portefeuille van de klant
     * dan moet ergens eerst worden uitgerekend:
     * - Wat was de hoeveelheid van de cryptomunt in de portefeuille vóór de 'transactie'?
     * - Is de uitkomst niet negatief (aka de klant heeft te weinig cryptomunten voor de transactie)?
     * - Set dan de nieuwe hoeveelheid in de portefeuille
     * - Dit lijkt me te horen bij de 'utilities' of eventueel 'service'
     */
    /*private PreparedStatement insertMemberStatement(int klantId, int cryptomuntId, double aantal, Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(
                "insert into portefeuille_table (klantId, cryptomuntId, aantal) values (?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, klantId);
        ps.setInt(2, cryptomuntId);
        ps.setDouble(3, aantal);
        return ps;
    }*/

    /**
     * @Author Carmen
     * Dit wordt een method om een RowMapper te maken, als we die nodig zouden hebben
     */
   /* private static class MemberRowMapper implements RowMapper<Member> {

        @Override
        public Member mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            long id = resultSet.getInt("id");
            String fn = resultSet.getString("full_name");
            String un = resultSet.getString("username");
            String pw = resultSet.getString("password");
            Member member = new Member(fn, un, pw);
            member.setId(id);
            return member;
        }
    }*/

    /**
     * Author: Carmen
     * Vraag mbt vullen methodes van DAOs zonder database: dat wordt dubbel werk, want eerst schrijven we ze
     * voor de Map/Set/etc, en daarna worden ze helemaal herschreven voor de database.
     * Beter om nu een database te maken? Hoort niet bij de Sprint. Overleggen met PO?
     */

    @Override
    public Portefeuille vindPortefeuilleInhoudMetID(int klantId) {
        return null;
    }

    @Override
    public void voegCryptomuntToe(int cryptomuntId, int klantId) {

    }

    @Override
    public void verwijderCryptomunt(int cryptomuntId, int klantId) {

    }

    /**
     * @Author Carmen
     * Deze methode heeft een link met 'transactie', een object/tabel wat we nog moeten bedenken en uitwerken
     */
    @Override
    public void updateSaldoCryptomunt(int cryptomuntId, int hoeveelheid, int klantId) {

    }
}
