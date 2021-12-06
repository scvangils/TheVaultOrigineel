package com.example.thevault.service;

import com.example.thevault.domain.mapping.repository.RootRepository;
import com.example.thevault.domain.model.Klant;
import com.example.thevault.domain.model.Rekening;
import com.example.thevault.support.exceptions.UserNotExistsException;
import org.iban4j.CountryCode;
import org.iban4j.Iban;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;


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
        Klant bestaandeKlant = new Klant(1, "Henknr1", "fdsaljkl", "Hello", 1890393, LocalDate.of(1991, 01, 12));
        Rekening rekeningExpected = new Rekening("INGB0001234567NL", 1000.0);
        bestaandeKlant.setRekening(rekeningExpected);
        rekeningExpected.setKlant(bestaandeKlant);
        rekeningExpected.setRekeningId(1);


        RootRepository mockRepo = Mockito.mock(RootRepository.class);

        Mockito.when(mockRepo.vindKlantByUsername(bestaandeKlant.getGebruikersnaam())).thenReturn(bestaandeKlant);
        Mockito.when(mockRepo.vindRekeningVanKlant(bestaandeKlant)).thenReturn(rekeningExpected);
        Mockito.when(mockRepo.vraagSaldoOpVanKlant(bestaandeKlant)).thenReturn(bestaandeKlant.getRekening().getSaldo());

        RekeningService rekeningServiceTest = new RekeningService(mockRepo);

        double actual = rekeningServiceTest.vraagSaldoOpVanKlant(bestaandeKlant);
        System.out.println(actual);
        double expected = 1000.0;

        assertThat(actual).isEqualTo(expected);
    }


    @Test
    void vraagSaldoOpVanNietBestaandeKlant() {
        Klant bestaatWel = new Klant(1, "Henknr1", "fdsaljkl", "Hello", 1890393, LocalDate.of(1991, 01, 12));
        Klant bestaatNiet = new Klant(2, "HarryBeste", "210jklf", "", 101212, LocalDate.of(1991, 01, 12));

        Rekening rekeningExpected = new Rekening("INGB0001234567NL", 1000);
        bestaatWel.setRekening(rekeningExpected);

        RootRepository mockRepo = Mockito.mock(RootRepository.class);

        Mockito.when(mockRepo.vindKlantByUsername(bestaatWel.getGebruikersnaam())).thenReturn(bestaatWel);
        Mockito.when(mockRepo.vindRekeningVanKlant(bestaatWel)).thenReturn(rekeningExpected);
        Mockito.when(mockRepo.vraagSaldoOpVanKlant(bestaatWel)).thenReturn(bestaatWel.getRekening().getSaldo());

        RekeningService rekeningServiceTest = new RekeningService(mockRepo);
        try{
            rekeningServiceTest.vraagSaldoOpVanKlant(bestaatNiet);
            fail("Moet een UserNotExistsException gooien");
        } catch (UserNotExistsException expected){
            System.out.println("Test geslaagd!");
        }
    }
}