package com.example.thevault.domain.mapping.dao;

import com.example.thevault.domain.model.Klant;
import com.example.thevault.domain.model.Rekening;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Map;

@Repository
public class JDBCRekeningDAO implements RekeningDAO {
    JdbcTemplate jdbcTemplate;

    private final Logger logger = LoggerFactory.getLogger(JDBCKlantDAO.class);

    @Autowired
    public JDBCRekeningDAO(JdbcTemplate jdbcTemplate) {
        super();
        this.jdbcTemplate = jdbcTemplate;
        logger.info("New JDBCRekeningDAO");
    }


    @Override
    public void slaRekeningOp(Rekening rekening) {

    }

    @Override
    public Rekening vindRekeningVanKlant(Klant klant) {
        return null;
    }

    @Override
    public double vraagSaldoOpVanKlant(Klant klant) {
        return 0.0;
    }

    @Override
    public void wijzigSaldoVanKlant(Klant klant, double bedrag) {
    }
}
