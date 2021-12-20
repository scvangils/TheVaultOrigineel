// Created by E.S. Olthof
// Creation date 12/15/2021

package com.example.thevault.domain.mapping.dao;

import com.example.thevault.domain.model.*;

import java.time.OffsetDateTime;
import java.util.List;

public interface TransactieDAO {

    Transactie slaTransactieOp(Transactie transactie);

    List<Transactie> geefTransactiesVanGebruiker(Gebruiker gebruiker);

    List<Transactie> geefTransactiesVanKlantInPeriode(Gebruiker klant, OffsetDateTime startDatum, OffsetDateTime eindDatum);

    List<Transactie> geefAlleTransactiesInPeriode(OffsetDateTime startDatum, OffsetDateTime eindDatum);

    List<Transactie> geefTransactiesVanKlantMetCryptomunt(Klant klant, Cryptomunt cryptomunt);

    List<Transactie> geefTransactiesVanGebruikerMetCryptomunt(Gebruiker gebruiker, Cryptomunt cryptomunt);


}
