package com.example.thevault.domain.mapping.dao;

import com.example.thevault.domain.model.Klant;
import com.example.thevault.domain.model.Rekening;

public interface RekeningDAO {

    Rekening slaRekeningOp(Rekening rekening);

    Rekening vindRekeningVanKlant(Klant klant);

    double vraagSaldoOpVanKlant(Klant klant);

    Rekening wijzigSaldoVanKlant(Klant klant, double bedrag);


}
