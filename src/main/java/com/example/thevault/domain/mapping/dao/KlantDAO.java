package com.example.thevault.domain.mapping.dao;

import com.example.thevault.domain.model.Klant;

import java.util.List;
import java.util.Optional;

public interface KlantDAO {
    Klant slaKlantOp(Klant klant);

    Klant vindKlantById(int gebruikerId);

    List<Klant> vindAlleKlanten();

    Klant vindKlantByGebruikersnaam(String gebruikersnaam);

    Klant verwijderKlant(Klant klant);

}
