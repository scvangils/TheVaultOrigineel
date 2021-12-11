package com.example.thevault.service;

import com.example.thevault.domain.mapping.dao.AssetDAO;
import com.example.thevault.domain.mapping.dao.KlantDAO;
import com.example.thevault.domain.mapping.dao.RekeningDAO;
import com.example.thevault.domain.mapping.repository.RootRepository;
import com.example.thevault.domain.model.Klant;
import com.example.thevault.support.BSNvalidator;
import com.example.thevault.support.exceptions.AgeTooLowException;
import com.example.thevault.support.exceptions.IncorrectBSNException;
import com.example.thevault.support.exceptions.PasswordNotSuitableException;
import com.example.thevault.support.hashing.BCryptWachtwoordHash;
import com.example.thevault.support.hashing.HashHelper;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;


class KlantServiceTest {


    private static Klant testKlant;
    private static Klant andereKlant;
    private static KlantService klantService;
    private static RootRepository mockRootRepository;


    @BeforeEach
    void setUp() {
        mockRootRepository = Mockito.mock(RootRepository.class);
        String gehashtWachtwoord = BCryptWachtwoordHash.hashWachtwoord("testWW");
        testKlant = new Klant("testKlant", gehashtWachtwoord, // klant is exact 18 vandaag
                null, null, "Jan", null, BSNvalidator.TESTBSN_VAN_RIVG,
                LocalDate.now().minusYears(KlantService.VOLWASSEN_LEEFTIJD));
         klantService = new KlantService(mockRootRepository);
        andereKlant = new Klant("andereKlant", gehashtWachtwoord, // klant is exact 18 vandaag
                null, null, "Jan", null, BSNvalidator.TESTBSN_VAN_RIVG,
                LocalDate.now().minusYears(KlantService.VOLWASSEN_LEEFTIJD));

    }



    @Test
    void vindKlantByUsername() {
        Mockito.when(mockRootRepository.vindKlantByGebruikersnaam("testKlant"))
                .thenReturn(testKlant);
        Klant expected = testKlant;
        Klant actual = klantService.vindKlantByGebruikersnaam(testKlant.getGebruikersnaam());
        assertThat(actual).isNotNull().isEqualTo(expected);
        actual = klantService.vindKlantByGebruikersnaam(andereKlant.getGebruikersnaam());
        assertThat(actual).isNull();
    }

    @Test
    void registreerKlant() {
        Mockito.when(mockRootRepository.slaKlantOp(testKlant)).thenReturn(testKlant);
        Klant expected = testKlant;
        Klant actual = klantService.registreerKlant(testKlant);
        assertThat(actual).as("Juiste klantgegevens zorgt voor goede registratie").isNotNull().isEqualTo(expected);
        testKlant.setGeboortedatum(testKlant.getGeboortedatum().plusDays(1)); // nu te jong om rekening te openen
        assertThatThrownBy(() -> klantService.registreerKlant(testKlant)).as("AgeTooLowException getest")
                .isInstanceOf(AgeTooLowException.class);
        testKlant.setGeboortedatum(testKlant.getGeboortedatum().minusDays(1));
        testKlant.setBsn(testKlant.getBsn() + 1);
        assertThatThrownBy(() -> klantService.registreerKlant(testKlant)).as("IncorrectBSNException getest")
                .isInstanceOf(IncorrectBSNException.class);
        testKlant.setBsn(testKlant.getBsn() - 1);
        testKlant.setWachtwoord("WwMet Spatie");
        assertThat(klantService.checkWachtwoordFormat(testKlant)).isFalse();
        assertThatThrownBy(() -> klantService.registreerKlant(testKlant)).as("Wachtwoord met spatie niet toegestaan Exception getest")
                .isInstanceOf(PasswordNotSuitableException.class);
        testKlant.setWachtwoord("Ww<8Kar");
        assertThat(klantService.checkWachtwoordLengte(testKlant)).isFalse();
        assertThatThrownBy(() -> klantService.registreerKlant(testKlant)).as("Wachtwoord minder dan 8 karakters niet toegestaan Exception getest")
                .isInstanceOf(PasswordNotSuitableException.class);

    }
    @Test
    void checkVolwassen(){
        assertThat(klantService.checkVolwassen(testKlant)).isTrue();
        testKlant.setGeboortedatum(testKlant.getGeboortedatum().plusDays(1)); // nu te jong om rekening te openen
        assertThat(klantService.checkVolwassen(testKlant)).isFalse();
        testKlant.setGeboortedatum(testKlant.getGeboortedatum().minusDays(4));
        assertThat(klantService.checkVolwassen(testKlant)).isTrue();

    }
    @Test
    void checkWachtwoordFormat(){
        testKlant.setWachtwoord("WwMet Spatie");
        assertThat(klantService.checkWachtwoordFormat(testKlant)).isFalse();
        testKlant.setWachtwoord("WwZonderSpatie");
        assertThat(klantService.checkWachtwoordFormat(testKlant)).isTrue();


    }
    @Test
    void checkWachtwoordLengte(){
        testKlant.setWachtwoord("Ww<8Kar");
        assertThat(klantService.checkWachtwoordLengte(testKlant)).isFalse();
        testKlant.setWachtwoord("8KarInWw");
        assertThat(klantService.checkWachtwoordLengte(testKlant)).isTrue();
    }

}