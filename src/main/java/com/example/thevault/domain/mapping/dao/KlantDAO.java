package com.example.thevault.domain.mapping.dao;

import com.example.thevault.domain.model.Klant;

import java.util.Optional;

public interface KlantDAO {
    void slaKlantOp(Klant klant);

    Klant vindKlantById(int id);

    Klant vindKlantByUsername(String name);

}
