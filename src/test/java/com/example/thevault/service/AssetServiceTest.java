package com.example.thevault.service;

import com.example.thevault.domain.mapping.repository.RootRepository;
import com.example.thevault.domain.model.Asset;
import com.example.thevault.domain.model.Cryptomunt;
import com.example.thevault.domain.model.Klant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.atIndex;
import static org.junit.jupiter.api.Assertions.*;

class AssetServiceTest {

    public static Klant testKlant;
    public static Asset testAsset1;
    public static Asset testAsset2;
    public static Asset testAsset3;
    public static Asset testAsset4;
    public static Asset testAsset5;
    public static RootRepository rootRepository;
    public static AssetService assetService;
    public static List<Asset> portefeuille;
    public static Cryptomunt testCryptomunt1;
    public static Cryptomunt testCryptomunt2;
    public static Cryptomunt testCryptomunt3;


    @BeforeEach
    void setUp() {
        testCryptomunt1 = new Cryptomunt(1, "CarmenCrypto", "CCR", 100.0);
        testCryptomunt2 = new Cryptomunt(2, "DigiCrypto", "DIG", 75.0);
        testCryptomunt3 = new Cryptomunt(3, "Coyne", "COY", 125.0);
        testAsset1 = new Asset(testCryptomunt1, 5.1);
        testAsset2 = new Asset(testCryptomunt2, 2.4);
        testAsset3 = new Asset(testCryptomunt3, 3.6);
        testAsset4 = new Asset(testCryptomunt1, 0.5);
        testAsset5 = new Asset(testCryptomunt1, 5.6);
        rootRepository = Mockito.mock(RootRepository.class);
        assetService = new AssetService(rootRepository);
        portefeuille = new ArrayList<>();
        portefeuille.add(testAsset1);
        portefeuille.add(testAsset2);
        portefeuille.add(testAsset3);
        testKlant = new Klant("Carmen", "Wachtwoord", portefeuille, null,
                "Carmen Rietdijk", null, 123456789, LocalDate.now());
    }

    @Test
    void geefInhoudPortefeuille() throws SQLException {
        Mockito.when(rootRepository.vulPortefeuilleKlant(1)).thenReturn(portefeuille);
        List<Asset> expected = portefeuille;
        List<Asset> actual = assetService.geefInhoudPortefeuille(1);
        assertThat(actual).as("Test geef inhoud portefeuille van testklant").isNotNull().isEqualTo(expected).
                contains(testAsset1, atIndex(0)).contains(testAsset2, atIndex(1)).contains(testAsset3, atIndex(2)).
                doesNotContain(testAsset4).hasSize(3).extracting(Asset::getCryptomunt).extracting(Cryptomunt::getName).
                contains("CarmenCrypto", "DigiCrypto", "Coyne").doesNotContain("BitCoin");
    }

    @Test
    void geefCryptomunt() {
        Mockito.when(rootRepository.geefAssetVanKlant(1, 1)).thenReturn(testAsset1);
        Asset expected = testAsset1;
        Asset actual = assetService.geefCryptomunt(1, 1);
        assertThat(actual).as("Test geef specifieke Asset van testklant").isNotNull().isEqualTo(expected).
                isIn(portefeuille).isNotEqualTo(testAsset2).isNotSameAs(testAsset3);
    }

    @Test
    void slaAssetOp() {
        Mockito.when(rootRepository.slaAssetVanKlantOp(1, testAsset2)).thenReturn(testAsset2);
        Asset expected = testAsset2;
        Asset actual = assetService.slaAssetOp(1, testAsset2);
        assertThat(actual).as("Test sla asset van testklant op").isNotNull().isEqualTo(expected).
                isIn(portefeuille).isNotEqualTo(testAsset3).isNotSameAs(testAsset4);
    }

    @Test
    void wijzigAsset() {
        Mockito.when(rootRepository.wijzigAssetVanKlant(1, testAsset4)).thenReturn(testAsset5);
        Asset expected = testAsset5;
        Asset actual = assetService.wijzigAsset(1, testAsset4);
        assertThat(actual).as("Test wijzigen van asset van testklant").isNotNull().isEqualTo(expected).
                isNotIn(portefeuille).isNotEqualTo(testAsset1).isNotSameAs(testAsset2);
    }
}