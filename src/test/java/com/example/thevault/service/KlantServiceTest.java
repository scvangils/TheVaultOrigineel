package com.example.thevault.service;

import com.example.thevault.domain.mapping.dao.KlantDAO;
import com.example.thevault.domain.mapping.repository.RootRepository;
import com.example.thevault.domain.model.Klant;
import com.example.thevault.support.hashing.HashHelper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class KlantServiceTest {
    static RootRepository rootRepository = Mockito.mock(RootRepository.class);
    private KlantService klantService = new KlantService(rootRepository);
    public static Klant testKlant;

    @BeforeAll
    static void setUp() {
        String gehashtWachtwoord = HashHelper.hashHelper("testWW");
        testKlant = new Klant(2, "testKlant", gehashtWachtwoord,
                null, null, "Jan", null, 145801354, LocalDate.now());


    }

    @Test
    void vindKlantByUsername() {
        Mockito.when(rootRepository.vindKlantByUsername("testKlant"))
                .thenReturn(testKlant);
        Klant expected = testKlant;
        Klant actual = klantService.vindKlantByUsername(testKlant.getGebruikersnaam());
        assertThat(actual).isNotNull().isEqualTo(expected);
    }

    @Test
    void registreerKlant() {
        Klant expected = testKlant;
        Klant actual = klantService.registreerKlant(testKlant);
    }

}