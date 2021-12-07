package com.example.thevault.domain.mapping.dao;

import com.example.thevault.domain.model.Klant;
import com.example.thevault.domain.model.Rekening;
import net.minidev.json.annotate.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class JDBCRekeningDAO implements RekeningDAO {
    JdbcTemplate jdbcTemplate;

    @JsonIgnore
    private final Logger logger = LoggerFactory.getLogger(JDBCKlantDAO.class);

    @Autowired
    public JDBCRekeningDAO(JdbcTemplate jdbcTemplate) {
        super();
        this.jdbcTemplate = jdbcTemplate;
        logger.info("New JDBCRekeningDAO");
    }

    @Override
    public Rekening slaRekeningOp(Rekening rekening) {
        return null;
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
    public Rekening wijzigSaldoVanKlant(Klant klant, double bedrag) {
        return null;
    }
}
