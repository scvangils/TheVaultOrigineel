package com.example.thevault.service;

import com.example.thevault.domain.mapping.repository.RootRepository;
import com.example.thevault.domain.model.*;
import com.example.thevault.domain.transfer.AssetDto;
import com.example.thevault.support.BSNvalidator;
import com.example.thevault.support.exceptions.TriggerAantalNietPositiefException;
import com.example.thevault.support.exceptions.TriggerPrijsNietPositiefException;
import com.example.thevault.support.hashing.BCryptWachtwoordHash;
import org.apache.commons.math3.util.Precision;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.*;
import static org.assertj.core.api.Assertions.*;

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
    public static Trigger testTriggerKoper1;
    public static Trigger testTriggerKoper2;
    public static Trigger testTriggerKoper3;
    public static Trigger testTriggerKoper4;
    public static Trigger testTriggerKoper5;
    public static Trigger testTriggerVerkoper1;
    public static Trigger testTriggerVerkoper2;
    public static Trigger testTriggerVerkoper3;
    public static Trigger testTriggerVerkoper4;
    public static Trigger testTriggerVerkoper5;
    public static Transactie testTransactie1;
    public static LocalDateTime testDatumTijd;
    public static List<Trigger> testTriggerKoperLijst1;
    public static List<Trigger> testTriggerKoperLijst2;
    public static List<Trigger> testTriggerVerkoperLijst1;
    public static List<Trigger> testTriggerVerkoperLijst2;


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
        testCryptomunt1 = new Cryptomunt(1, "CarmenCrypto", "CCR" );
        testCryptomunt2 = new Cryptomunt(2, "DigiCrypto", "DIG");
        testCryptomunt3 = new Cryptomunt(3, "Coyne", "COY");
        testAsset1 = new Asset(testCryptomunt1, 4.0);
        testAsset2 = new Asset(testCryptomunt2, 2.4);
        testAsset3 = new Asset(testCryptomunt3, 3.6);
        testAsset4 = new Asset(testCryptomunt1, 0.5);
        portefeuille = new ArrayList<>();
        portefeuille.add(testAsset1);
        portefeuille.add(testAsset2);
        portefeuille.add(testAsset3);
        testTriggerKoper1 = new TriggerKoper(testKlant, testCryptomunt1, 1100.0, 2);
        testTriggerKoper2 = new TriggerKoper(testKlant, testCryptomunt1, 1100.0, 0);
        testTriggerKoper3 = new TriggerKoper(testKlant, testCryptomunt1, 1100.0, -1);
        testTriggerKoper1 = new TriggerKoper(testKlant, testCryptomunt1, 1100.0, 2);
        testTriggerKoper2 = new TriggerKoper(testKlant, testCryptomunt1, 1100.0, 0);
        testTriggerKoper3 = new TriggerKoper(testKlant, testCryptomunt1, 1100.0, -1);
        testTriggerKoper4 = new TriggerKoper(testKlant, testCryptomunt2, 50, 2);
        testTriggerKoper5 = new TriggerKoper(testKlant, testCryptomunt3, 500.0, 2);
        testTriggerKoperLijst1 = new ArrayList<>();
        testTriggerKoperLijst1.add(testTriggerKoper4);
        testTriggerKoperLijst1.add(testTriggerKoper5);
        testTriggerKoperLijst2 = new ArrayList<>();
        testTriggerKoperLijst2.add(testTriggerKoper4);
        testTriggerVerkoper1 = new TriggerVerkoper(testKlant, testCryptomunt1, 1000.0, 2);
        testTriggerVerkoper2 = new TriggerVerkoper(testKlant, testCryptomunt1, 0, 2);
        testTriggerVerkoper3 = new TriggerVerkoper(testKlant, testCryptomunt1, -1000.0, 2);
        testTriggerVerkoper4 = new TriggerVerkoper(testKlant, testCryptomunt1, 1000.0, 2);
        testTriggerVerkoper5 = new TriggerVerkoper(testKlant, testCryptomunt1, 1000.0, 2);
        testTriggerVerkoperLijst1 = new ArrayList<>();
        testTriggerVerkoperLijst1.add(testTriggerVerkoper4);
        testTriggerVerkoperLijst1.add(testTriggerVerkoper5);
        testTriggerVerkoperLijst2 = new ArrayList<>();
        testTriggerVerkoperLijst2.add(testTriggerVerkoper4);
        testTransactie1 = new Transactie(LocalDateTime.of(2022, 1, 1, 0, 0, 0),
                testTriggerKoper1, testTriggerVerkoper1);
        testDatumTijd = LocalDateTime.of(2022, 1, 1, 0, 0, 0);
    }

    @Test
    void isKoper() {
        boolean trueTest = TriggerService.isKoper(testTriggerKoper1);
        assertThat(trueTest).as("Bij een TriggerKoper moet true teruggegeven worden").isTrue();
        boolean falseTest = TriggerService.isKoper(testTriggerVerkoper1);
        assertThat(falseTest).as("Bij een TriggerVerkoper moet false teruggegeven worden").isFalse();
    }

    @Test
    void checkTransactieMogelijk() {

    }

    @Test
    void sluitTransactieAfMetKlant() {
        Mockito.when(mockTransactieService.sluitTransactie(testDatumTijd,
                testTriggerKoper1, testTriggerVerkoper1)).thenReturn(testTransactie1);
        Transactie actual = triggerService.sluitTransactieAfMetKlant(testDatumTijd, testTriggerKoper1, testTriggerVerkoper1);
        Transactie expected = testTransactie1;
        assertThat(actual).isNotNull().isEqualTo(expected);
    }

    @Test
    void triggerMogelijkQuaFondsen() {

    }

    @Test
    void schaduwSaldoNietToereikend() {
        Mockito.when(mockRootRepository.vraagSaldoOpVanGebruiker(testKlant))
                .thenReturn(2300.0 + 3 * mockTransactieService.DEEL_TRANSACTION_FEE_KOPER * Bank.getInstance().getFee());
        Mockito.when(mockRootRepository.vindTriggersByGebruiker(testKlant, triggerService.KOPER)).thenReturn(testTriggerKoperLijst1);
        double actual = triggerService.schaduwSaldo(testKlant);
        System.out.println(actual);
        double expected = 1200 + mockTransactieService.DEEL_TRANSACTION_FEE_KOPER * Bank.getInstance().getFee();
        System.out.println(expected);
        assertThat(Precision.equals(actual, expected)).as("Het schaduwsaldo is hier nog niet negatief").isTrue();
        double kostenNieuweTrigger = testTriggerKoper1.getTriggerPrijs() * testTriggerKoper1.getAantal()
                + mockTransactieService.DEEL_TRANSACTION_FEE_KOPER * Bank.getInstance().getFee();
        assertThat((actual - kostenNieuweTrigger)).as("Nu blijkt het saldo niet toereikend").isLessThan(0.0);
    }

    @Test
    void schaduwSaldoToereikend() {
        Mockito.when(mockRootRepository.vraagSaldoOpVanGebruiker(testKlant))
                .thenReturn(2300.0 + 3 * mockTransactieService.DEEL_TRANSACTION_FEE_KOPER * Bank.getInstance().getFee());
        Mockito.when(mockRootRepository.vindTriggersByGebruiker(testKlant, triggerService.KOPER)).thenReturn(testTriggerKoperLijst2);
        double actual = triggerService.schaduwSaldo(testKlant);
        System.out.println(actual);
        double expected = 2200 + 2 * mockTransactieService.DEEL_TRANSACTION_FEE_KOPER * Bank.getInstance().getFee();
        System.out.println(expected);
        assertThat(Precision.equals(actual, expected)).as("Het schaduwsaldo is hier nog niet negatief").isTrue();
        double kostenNieuweTrigger = testTriggerKoper1.getTriggerPrijs() * testTriggerKoper1.getAantal()
                + mockTransactieService.DEEL_TRANSACTION_FEE_KOPER * Bank.getInstance().getFee();
        assertThat((actual - kostenNieuweTrigger)).as("Nu blijkt het saldo toereikend").isNotNegative();
    }

    @Test
    void schaduwAantalAsset() {
    }

    @Test
    void slaTriggerOp() {
    }

    @Test
    void vindMatchKoperNaarVerkoper() {
        Mockito.when(mockRootRepository.vindMatch(testTriggerKoper1)).thenReturn(testTriggerVerkoper1);
        Trigger actual = triggerService.vindMatch(testTriggerKoper1);
        Trigger expected = testTriggerVerkoper1;
        assertThat(actual).as("Koper vind verkoopmatch moet kloppen").isNotNull().isEqualTo(expected);
    }
    @Test
    void vindMatchVerkoperNaarKoper() {
        Mockito.when(mockRootRepository.vindMatch(testTriggerVerkoper1)).thenReturn(testTriggerKoper1);
        Trigger actual = triggerService.vindMatch(testTriggerVerkoper1);
        Trigger expected = testTriggerKoper1;
        assertThat(actual).as("Verkoper vind koopmatch moet kloppen").isNotNull().isEqualTo(expected);
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
        assertThatThrownBy(() -> TriggerService.checkAantalPositief(testTriggerKoper2)).as("triggerAantal mag niet 0 zijn")
                .isInstanceOf(TriggerAantalNietPositiefException.class);
        assertThatThrownBy(() -> TriggerService.checkAantalPositief(testTriggerKoper3)).as("triggerAantal mag niet negatief zijn")
                .isInstanceOf(TriggerAantalNietPositiefException.class);

    }
    @Test
    void checkPrijsPositief() {
        assertThatThrownBy(() -> TriggerService.checkPrijsPositief(testTriggerVerkoper2)).as("triggerPrijs mag niet 0 zijn")
                .isInstanceOf(TriggerPrijsNietPositiefException.class);
        assertThatThrownBy(() -> TriggerService.checkPrijsPositief(testTriggerVerkoper3)).as("triggerPrijs mag niet negatief zijn")
                .isInstanceOf(TriggerPrijsNietPositiefException.class);
    }

    @Test
    void vergelijkAlleKoopTriggersMetBeschikbareFondsen() {
    }

    @Test
    void vergelijkAlleVerkoopTriggersMetBeschikbareFondsen() {
    }

}