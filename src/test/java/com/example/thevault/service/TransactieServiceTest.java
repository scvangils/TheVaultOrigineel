// Created by E.S. Olthof
// Creation date 15-12-2020

package com.example.thevault.service;

import com.example.thevault.domain.mapping.dao.JDBCRekeningDAO;
import com.example.thevault.domain.mapping.dao.JDBCTransactieDAO;
import com.example.thevault.domain.mapping.dao.TransactieDAO;
import com.example.thevault.domain.mapping.repository.RootRepository;
import com.example.thevault.domain.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import com.example.thevault.domain.model.Klant;
import com.example.thevault.domain.model.Rekening;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.isA;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;

class TransactieServiceTest {

    private static Klant testKlant1;
    private static Klant testKlant2;
    private static Klant testKlant3;
    private static Transactie testTransactie1;
    private static Transactie testTransactie2;
    private static Transactie testTransactie3;
    private static Cryptomunt testCryptomunt1;
    private static Cryptomunt testCryptomunt2;
    private static Cryptomunt testCryptomunt3;
    private static Rekening testRekening1;
    private static Rekening testRekening11;
    private static Rekening testRekening2;
    private static Rekening testRekening22;
    private static Rekening testRekening3;
    private static KlantService mockKlantService;
    private static RekeningService mockRekeningService;
    private static TransactieService mockTransactieService;
    private static RootRepository mockRootRepository;
    private static JDBCTransactieDAO mockTransactieDAO;
    private static JDBCRekeningDAO mockRekeningDAO;

    private static Asset testAsset1;
    private static Asset testAsset2;
    private static Asset testAsset3;
    private static Asset testAsset4;
    private static Asset testAsset5;

    /*public Transactie(OffsetDateTime momentTransactie,
                      Klant verkoper, Cryptomunt cryptomunt, double bedrag, double aantal,
                      Klant koper) {*/

    @BeforeEach
    void setUp() {
        testKlant1 = new Klant( "Henknr1", "fdsaljkl", "Hello", 1890393, LocalDate.of(1991, 1, 12));
        testKlant2 = new Klant( "HarryBeste", "210jklf", "", 101212, LocalDate.of(1991, 1, 12));
        testKlant3 = new Klant( "ThomasBeste", "831hgtr", "", 1528719, LocalDate.of(1990, 5, 10));

        testCryptomunt1 = new Cryptomunt(1, "CarmenCrypto", "CCR" );
//        testCryptoWaarde1 = new CryptoWaarde("20211214CCR", testCryptomunt1, 100.0, LocalDate.now());
        testCryptomunt2 = new Cryptomunt(2, "DigiCrypto", "DIG");
//        testCryptoWaarde2 = new CryptoWaarde("20211214DIG", testCryptomunt1, 75.0, LocalDate.now());
        testCryptomunt3 = new Cryptomunt(3, "Coyne", "COY");
//        testCryptoWaarde3 = new CryptoWaarde("20211214COY", testCryptomunt1, 125.0, LocalDate.now());
        testAsset1 = new Asset(testCryptomunt1, 5.1);
        testAsset2 = new Asset(testCryptomunt2, 2.4);
        testAsset3 = new Asset(testCryptomunt3, 3.6);

        testTransactie1 = new Transactie(OffsetDateTime.now(), testKlant1, testCryptomunt1, 1000, 1, testKlant2);
        testTransactie2 = new Transactie(OffsetDateTime.now(), testKlant2, testCryptomunt2, 9000, 1, testKlant1);
        testTransactie3 = new Transactie(OffsetDateTime.now(), testKlant1, testCryptomunt3, 10000, 1, testKlant2);

        testRekening1 = new Rekening("NL20RABO9876543", 10000.0);
        testRekening11 =new Rekening("NL20RABO9876543", 9000.0);
        testRekening2 = new Rekening("INGB0001234567NL", 20000.0);
        testRekening22 = new Rekening("INGB0001234567NL", 20000.0);
        testRekening3 = new Rekening("INGB0001234567NL", 29000.0);

        mockRootRepository = Mockito.mock(RootRepository.class);
        mockRekeningService = new RekeningService(mockRootRepository);
        mockKlantService = new KlantService(mockRootRepository);
        mockTransactieService = new TransactieService(mockRootRepository, mockKlantService, mockRekeningService);



    }
    /**
     * testklant1 heeft testRekening1 en is koper in transactie1 (testklant2 is verkoper)
    *
    * */

    @Test
    void sluitTransactie() {
        testKlant1.setRekening(testRekening1);
        List<Asset> testPortefeuille1 = new ArrayList<>();
        testPortefeuille1.add(testAsset1);
        testKlant1.setPortefeuille(testPortefeuille1);

        testKlant2.setRekening(testRekening2);
        testKlant3.setRekening(testRekening3);

        testTransactie1 = new Transactie(OffsetDateTime.now(), testKlant1, testCryptomunt1, 1000, 1, testKlant2);
        Mockito.when(mockRootRepository.vindRekeningVanKlant(testKlant1))
                .thenReturn(testRekening1);
        Mockito.when(mockRootRepository.slaRekeningOp(testRekening1))
                .thenReturn(testRekening1);
        Mockito.when(mockRootRepository.wijzigSaldoVanKlant(testKlant1, 1000)).thenReturn(testRekening11);
        Mockito.when(mockRekeningDAO.wijzigSaldoVanKlant(testKlant1, 1000)).thenReturn(testRekening11);
        Mockito.when(mockRekeningDAO.vindRekeningVanKlant(testKlant1)).thenReturn(testRekening11);

        Mockito.when(mockRekeningService.wijzigSaldo(testKlant1, (testKlant1.getRekening().getSaldo()-1000)))
                .thenReturn(testRekening11);

        Mockito.when(mockRekeningService.wijzigSaldo(testKlant2, (testKlant2.getRekening().getSaldo()+1000)))
                .thenReturn(testRekening11);
        Mockito.when(mockRootRepository.slaTransactieOp(testTransactie1)).thenReturn(testTransactie1);

        assertThat(mockTransactieService.sluitTransactie(testKlant1, testCryptomunt1,
                1000, 1, testKlant2)).isEqualTo(testTransactie1);

    }


/*    public Transactie sluitTransactie(Klant verkoper, Cryptomunt cryptomunt,
                                      double bedrag, double aantal, Klant koper) {

        // handel eventuele exceptions af
        saldoTooLowExceptionHandler(koper, bedrag);
        notEnoughCryptoExceptionHandler(verkoper, cryptomunt, aantal);

        //wijzig saldo van de klanten op basis van de transactie
        rekeningService.wijzigSaldo(koper, (koper.getRekening().getSaldo() - bedrag));
        rekeningService.wijzigSaldo(verkoper, verkoper.getRekening().getSaldo() + bedrag);

        // maak nieuwe transactie aan
        Transactie transactie = new Transactie(OffsetDateTime.now(), verkoper, cryptomunt, bedrag, aantal, koper);
        return slaTransactieOp(transactie);
    }

    public Transactie slaTransactieOp(Transactie transactie){
        return rootRepository.slaTransactieOp(transactie);
    }*/

    @Test
    void slaTransactieOp() {
    }

    @Test
    void notEnoughCryptoExceptionHandler() {
    }

    @Test
    void saldoTooLowExceptionHandler() {
    }
}