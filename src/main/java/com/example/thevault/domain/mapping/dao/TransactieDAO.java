// Created by E.S. Olthof
// Creation date 12/15/2021

package com.example.thevault.domain.mapping.dao;

import com.example.thevault.domain.model.Bank;
import com.example.thevault.domain.model.Cryptomunt;
import com.example.thevault.domain.model.Klant;
import com.example.thevault.domain.model.Transactie;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface TransactieDAO {

    Transactie slaTransactieOp(Transactie transactie);

    List<Transactie> geefTransactiesVanKlant(Klant klant);

    List<Transactie> geefTransactiesVanBank();

    List<Transactie> geefTransactiesVanKlantInPeriode(Klant klant, OffsetDateTime startDatum, OffsetDateTime eindDatum);

    List<Transactie> geefAlleTransactiesInPeriode(OffsetDateTime startDatum, OffsetDateTime eindDatum);

    List<Transactie> geefTransactiesVanKlantMetCryptomunt(Klant klant, Cryptomunt cryptomunt);




}
