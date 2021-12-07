package com.example.thevault.domain.mapping.repository;

import com.example.thevault.domain.mapping.dao.AssetDAO;
import com.example.thevault.domain.mapping.dao.KlantDAO;
import com.example.thevault.domain.mapping.dao.RekeningDAO;
import com.example.thevault.domain.model.Asset;
import com.example.thevault.domain.model.Cryptomunt;
import com.example.thevault.domain.model.Klant;
import com.example.thevault.domain.model.Rekening;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RootRepositoryTest {

    public static RootRepository rootRepository;
    private static KlantDAO klantDAO;
    private static AssetDAO assetDAO;
    private static RekeningDAO rekeningDAO;
    private static Klant testKlant;
    public static Asset testAsset1;
    public static Asset testAsset2;
    public static Asset testAsset3;
    public static List<Asset> portefeuille;
    public static Cryptomunt testCryptomunt1;
    public static Cryptomunt testCryptomunt2;
    public static Cryptomunt testCryptomunt3;
    public static Rekening rekening;


    @BeforeEach
    void setUp() {
        klantDAO = Mockito.mock(KlantDAO.class);
        assetDAO = Mockito.mock(AssetDAO.class);
        rekeningDAO = Mockito.mock(RekeningDAO.class);
        rootRepository = new RootRepository(klantDAO, assetDAO, rekeningDAO);
        testCryptomunt1 = new Cryptomunt(1, "CarmenCrypto", "CCR", 100.0);
        testCryptomunt2 = new Cryptomunt(2, "DigiCrypto", "DIG", 75.0);
        testCryptomunt3 = new Cryptomunt(3, "Coyne", "COY", 125.0);
        testAsset1 = new Asset(testCryptomunt1, 5.1);
        testAsset2 = new Asset(testCryptomunt2, 2.4);
        testAsset3 = new Asset(testCryptomunt3, 3.6);
        portefeuille = new ArrayList<>();
        portefeuille.add(testAsset1);
        portefeuille.add(testAsset2);
        portefeuille.add(testAsset3);
        rekening = new Rekening("INGB0001234567NL", 1000.0);
        testKlant = new Klant("Huub", "PWHuub", portefeuille, rekening, "Huub", null,
                0, null);
    }

    @Test
    void slaKlantOp() {
        Mockito.when(klantDAO.slaKlantOp(testKlant)).thenReturn(testKlant);
        Klant expected = testKlant;
        Klant actual = rootRepository.slaKlantOp(testKlant);
        assertThat(actual).as("Test opslaan van klant").isNotNull().isEqualTo(expected).
                hasNoNullFieldsOrPropertiesExcept("adres", "geboortedatum").asString().startsWith("Klant{").
                contains("Huub").doesNotContain("Carmen");
    }

    @Test
    void vindKlantByGebruikersnaam() {
        Mockito.when(klantDAO.vindKlantByGebruikersnaam("Huub")).thenReturn(testKlant);
        Klant expected = testKlant;
        Klant actual = rootRepository.vindKlantByGebruikersnaam("Huub");
        assertThat(actual).as("Testen zoeken klant op gebruikersnaam").isNotNull().isEqualTo(expected).
                hasNoNullFieldsOrPropertiesExcept("adres", "geboortedatum").asString().startsWith("Klant{").
                contains("Huub").doesNotContain("Carmen");
    }

    @Test
    void slaRekeningOp() {
    }

    @Test
    void vindRekeningVanKlant() {
        Mockito.when(rekeningDAO.vindRekeningVanKlant(testKlant)).thenReturn(rekening);
        Rekening expected = rekening;
        Rekening actual = rootRepository.vindRekeningVanKlant(testKlant);
        assertThat(actual).as("Test vraag rekening op van testklant").isNotNull().isEqualTo(expected).
                hasNoNullFieldsOrProperties().extracting("iban", "saldo").contains("INGB0001234567NL").
                contains("1000.0");


    }

    @Test
    void vraagSaldoOpVanKlant() {
    }

    @Test
    void wijzigSaldoVanKlant() {
    }

    @Test
    void vulPortefeuilleKlant() {
    }

    @Test
    void geefAssetVanKlant() {
    }

    @Test
    void slaAssetVanKlantOp() {
    }

    @Test
    void wijzigAssetVanKlant() {
    }
}