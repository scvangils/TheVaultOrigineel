package com.example.thevault.domain.mapping.dao;

import com.example.thevault.domain.model.Rekening;

public interface RekeningDAO {

    void slaRekeningOp(Rekening rekening);

    Rekening vindRekeningMetKlantId(int id);

    //Rekening vindRekeningMetIban(String iban);

    Rekening vraagSaldoOpMetKlantId(int id);

    Rekening wijzigSaldoMetKlantId(int id, double bedrag);

    Rekening wijzigIbanMetKlantId(int id, String iban);
}
