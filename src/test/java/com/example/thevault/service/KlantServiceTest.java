package com.example.thevault.service;

import com.example.thevault.domain.mapping.repository.RootRepository;
import com.example.thevault.domain.model.Klant;
import com.example.thevault.support.hashing.HashHelper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class KlantServiceTest {

    public static Klant testKlant;

    @BeforeAll
    static void setUp() {
        String gehashtWachtwoord = HashHelper.hashHelper("testWW");
        testKlant = new Klant("testKlant", gehashtWachtwoord,
                null, null, "Jan", null, 145801354, LocalDate.now());


    }

    @Test
    void vindKlantByUsername() {
        RootRepository rootRepository = Mockito.mock(RootRepository.class);
        KlantService klantService = new KlantService(rootRepository);
        Mockito.when(rootRepository.vindKlantByGebruikersnaam("testKlant"))
                .thenReturn(testKlant);
        Klant expected = testKlant;
        Klant actual = klantService.vindKlantByGebruikersnaam(testKlant.getGebruikersnaam());
        assertThat(actual).isNotNull().isEqualTo(expected);
    }

/*    @Test
    void registreerKlant() {
        KlantDAO mockKlantDAO = Mockito.mock(KlantDAO.class);
        Mockito.when(mockKlantDAO.slaKlantOp(testKlant)).
        RekeningDAO mockRekeningDAO = Mockito.mock(RekeningDAO.class);
        Klant expected = testKlant;
        Klant actual = new KlantService(new RootRepository(mockKlantDAO, mockRekeningDAO)).registreerKlant(testKlant);
        System.out.println(actual);
        assertThat(actual).isNotNull().isEqualTo(expected);
    }*/

}