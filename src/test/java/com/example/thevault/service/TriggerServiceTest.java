package com.example.thevault.service;

import com.example.thevault.domain.mapping.repository.RootRepository;
import com.example.thevault.domain.model.*;
import com.example.thevault.domain.transfer.AssetDto;
import com.example.thevault.support.BSNvalidator;
import com.example.thevault.support.hashing.BCryptWachtwoordHash;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TriggerServiceTest {

    private static Gebruiker testKlant;
    private static Gebruiker andereKlant;
    private static TriggerService triggerService;
    private static TransactieService mockTransactieService;
    private static RootRepository mockRootRepository;
    public static List<Asset> portefeuille;
    public static Cryptomunt testCryptomunt1;
    public static Cryptomunt testCryptomunt2;
    public static Cryptomunt testCryptomunt3;
    public static Asset testAsset1;
    public static Asset testAsset2;
    public static Asset testAsset3;
    public static Asset testAsset4;
    @BeforeEach
    void setUp() {
        mockRootRepository = Mockito.mock(RootRepository.class);
        mockTransactieService = Mockito.mock(TransactieService.class);
        testKlant = new Klant("testKlant", "testWachtWoord",
                "Jan", null, BSNvalidator.TESTBSN_VAN_RIVG,
                LocalDate.now().minusYears(KlantService.VOLWASSEN_LEEFTIJD));
        triggerService = new TriggerService(mockRootRepository, mockTransactieService);
        andereKlant = new Klant("andereKlant", "anderWachtWoord",
                "Jan", null, BSNvalidator.TESTBSN_VAN_RIVG,
                LocalDate.now().minusYears(KlantService.VOLWASSEN_LEEFTIJD));
        /**
         * @Author Carmen
         * Hieronder alle noodzakelijke input voor de test van portefeuille DTOs
         */
        testCryptomunt1 = new Cryptomunt(1, "CarmenCrypto", "CCR" );
        testCryptomunt2 = new Cryptomunt(2, "DigiCrypto", "DIG");
        testCryptomunt3 = new Cryptomunt(3, "Coyne", "COY");
        testAsset1 = new Asset(testCryptomunt1, 5.1);
        testAsset2 = new Asset(testCryptomunt2, 2.4);
        testAsset3 = new Asset(testCryptomunt3, 3.6);
        testAsset4 = new Asset(testCryptomunt1, 0.5);
        portefeuille = new ArrayList<>();
        portefeuille.add(testAsset1);
        portefeuille.add(testAsset2);
        portefeuille.add(testAsset3);
    }

    @Test
    void checkTriggerKoper() {
    }

    @Test
    void checkTransactieMogelijk() {
    }

    @Test
    void sluitTransactieAfMetKlant() {
    }

    @Test
    void triggerMogelijkQuaFondsen() {
    }

    @Test
    void schaduwSaldo() {
    }

    @Test
    void schaduwAantalAsset() {
    }

    @Test
    void slaTriggerOp() {
    }

    @Test
    void vindMatch() {
    }

    @Test
    void verwijderTrigger() {
    }

    @Test
    void vindAlleTriggers() {
    }

    @Test
    void vindTriggersByGebruiker() {
    }

    @Test
    void checkAantalPositief() {
    }

    @Test
    void checkPrijs() {
    }

    @Test
    void vergelijkAlleKoopTriggersMetBeschikbareFondsen() {
    }

    @Test
    void vergelijkAlleVerkoopTriggersMetBeschikbareFondsen() {
    }

    @Test
    void isKoper() {
    }
}