package com.example.thevault.domain.mapping.dao;

import com.example.thevault.domain.model.Rekening;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class JDBCRekeningDAO implements RekeningDAO {

    private final Logger logger = LoggerFactory.getLogger(JDBCKlantDAO.class);

    public JDBCRekeningDAO() {
        super();
        logger.info("New JDBCRekeningDAO");
    }

    @Override
    public void slaRekeningOp(Rekening rekening) {

    }

    @Override
    public Rekening vindRekeningMetKlantId(int id) {
        return null;
    }

    @Override
    public Rekening vraagSaldoOpMetKlantId(int id) {
        return null;
    }

    @Override
    public Rekening wijzigSaldoMetKlantId(int id, double bedrag) {
        return null;
    }

    @Override
    public Rekening wijzigIbanMetKlantId(int id, String iban) {
        return null;
    }

}
