package com.example.thevault.domain.mapping.dao;

import com.example.thevault.domain.model.Klant;
import com.example.thevault.domain.model.Rekening;

/**
 * @Author: Ju-Sen Cheung
 * Beschrijving: De interface voor de DAO voor Rekening, waar enkele methodes instaan voor CRUD van Rekening.
 */

public interface RekeningDAO {

    /**
     * Met deze methode wordt er een rekening opgeslagen.
     * @param rekening is de rekening die is aangemaakt bij het registreren van de klant of een rekening waarvan het
     * saldo gewijzigd wordt na een transactie.
     * @return rekening die is opgeslagen na registratie of na een wijziging
     */
    Rekening slaRekeningOp(Rekening rekening);

    /**
     * Met deze methode kan je een rekening terugvinden als je de klant meegeeft.
     * @param klant is de klant van wie de rekening wordt opgevraagd.
     * @return rekening waarvan je de iban en het saldo van klant kunt zien
     */
    Rekening vindRekeningVanKlant(Klant klant);

    /**
     * Met deze methode kan je het saldo van de rekening opvragen als je de klant meegeeft.
     * @param klant is de klant van wie het rekeningsaldo wordt opgevraagd.
     * @return het saldo van de rekening.
     */
    double vraagSaldoOpVanKlant(Klant klant);

    /**
     * Met deze methode kan je het saldo van de rekening wijzigen als je de klant en het transactiebedrag meegeeft.
     * Het wijzigen van het saldo gebeurt doordat je een cryptomunt koopt of verkoopt via een transactie.
     * @param klant is de klant bij wie een transactie plaatsvindt.
     * @param transactieBedrag is het bedrag dat bij het rekeningsaldo opgeteld of afgetrokken wordt.
     * @return Als er voldoende saldo is voor de transactie, dan wordt het saldo aangepast. Zo niet, dan komt er een
     * bericht dat het saldo niet toereikend is.
     */
    Rekening wijzigSaldoVanKlant(Klant klant, double transactieBedrag);


}
