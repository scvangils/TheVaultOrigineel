package com.example.thevault.service;

import com.example.thevault.domain.mapping.repository.RootRepository;
import com.example.thevault.domain.model.Klant;
import com.example.thevault.domain.model.Rekening;
import com.example.thevault.support.exceptions.UserNotExistsException;
import org.h2.engine.User;
import org.iban4j.CountryCode;
import org.iban4j.Iban;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

//import static org.junit.jupiter.api.Assertions.*;

class RekeningServiceTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void createIban() {
        Iban iban = new Iban.Builder().countryCode(CountryCode.NL).bankCode("TVLT").buildRandom();
        assertThat(iban.toString()).contains("NL");
    }

    @Test
    void vraagSaldoOpVanKlant() {
        /** TO DO
         * Test aanmaken vraag saldo op, eerst alle id's weghalen bij Gebruiker, klant en rekening
         */
    }

    @Test
    void vraagSaldoOpVanNietBestaandeKlant() {
        /** TO DO
         * Test aanmaken vraag saldo op, eerst alle id's weghalen bij Gebruiker, klant en rekening
         */
    }
}