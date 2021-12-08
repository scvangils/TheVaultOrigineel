package com.example.thevault.service;

import com.example.thevault.domain.mapping.dao.AssetDAO;
import com.example.thevault.domain.mapping.dao.KlantDAO;
import com.example.thevault.domain.mapping.dao.RekeningDAO;
import com.example.thevault.domain.mapping.repository.RootRepository;
import com.example.thevault.domain.model.Klant;
import com.example.thevault.support.BSNvalidator;
import com.example.thevault.support.exceptions.AgeTooLowException;
import com.example.thevault.support.exceptions.IncorrectBSNException;
import com.example.thevault.support.hashing.BCryptWachtwoordHash;
import com.example.thevault.support.hashing.HashHelper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;


class KlantServiceTest {


    private static Klant testKlant;
    private static KlantService klantService;
    private static RootRepository mockRootRepository;


    @BeforeAll
    static void setUp() {
        mockRootRepository = Mockito.mock(RootRepository.class);
        String gehashtWachtwoord = BCryptWachtwoordHash.hashWachtwoord("testWW");
        testKlant = new Klant("testKlant", gehashtWachtwoord, // klant is exact 18 vandaag
                null, null, "Jan", null, BSNvalidator.TESTBSN_VAN_RIVG,
                LocalDate.now().minusYears(KlantService.VOLWASSEN_LEEFTIJD));
         klantService = new KlantService(mockRootRepository);

    }

    @Test
    void vindKlantByUsername() {
        Mockito.when(mockRootRepository.vindKlantByGebruikersnaam("testKlant"))
                .thenReturn(testKlant);
        Klant expected = testKlant;
        Klant actual = klantService.vindKlantByGebruikersnaam(testKlant.getGebruikersnaam());
        assertThat(actual).isNotNull().isEqualTo(expected);
    }

    @Test
    void registreerKlant() {
        Mockito.when(mockRootRepository.slaKlantOp(testKlant)).thenReturn(testKlant);
        Klant expected = testKlant;
        Klant actual = klantService.registreerKlant(testKlant);
        assertThat(actual).isNotNull().isEqualTo(expected);
        testKlant.setGeboortedatum(testKlant.getGeboortedatum().plusDays(1)); // nu te jong om rekening te openen
        assertThatThrownBy(() -> klantService.registreerKlant(testKlant)).isInstanceOf(AgeTooLowException.class);
        testKlant.setGeboortedatum(testKlant.getGeboortedatum().minusDays(1));
        testKlant.setBsn(testKlant.getBsn() + 1);
        assertThatThrownBy(() -> klantService.registreerKlant(testKlant)).isInstanceOf(IncorrectBSNException.class);
        testKlant.setBsn(testKlant.getBsn() + 1);

    }
    @Test
    void checkVolwassen(){
        assertThat(klantService.checkVolwassen(testKlant)).isTrue();
        testKlant.setGeboortedatum(testKlant.getGeboortedatum().plusDays(1)); // nu te jong om rekening te openen
        assertThat(klantService.checkVolwassen(testKlant)).isFalse();
    }
    @Test
    void checkWachtwoordFormat(){

    }

}