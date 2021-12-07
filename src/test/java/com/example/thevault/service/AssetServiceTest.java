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
import static org.junit.jupiter.api.Assertions.*;

class AssetServiceTest {

    public static Klant testKlant;
    public static Asset testAsset1;
    public static Asset testAsset2;
    public static Asset testAsset3;
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
        rootRepository = Mockito.mock(RootRepository.class);
        assetService = new AssetService(rootRepository);
        portefeuille = new ArrayList<>();
        portefeuille.add(testAsset1);
        portefeuille.add(testAsset2);
        portefeuille.add(testAsset3);
        testKlant = new Klant(1,"Carmen","Wachtwoord", portefeuille, null,
                "Carmen Rietdijk", null, 123456789, LocalDate.now());
    }

    @Test
    void geefInhoudPortefeuille() throws SQLException {
        Mockito.when(rootRepository.vulPortefeuilleKlant(1)).thenReturn(portefeuille);
        List<Asset> expected = portefeuille;
        List<Asset> actual = assetService.geefInhoudPortefeuille(1);
        assertThat(actual).isNotNull().isEqualTo(expected);
    }

    @Test
    void geefCryptomunt() {
    }

    @Test
    void slaAssetOp() {
    }

    @Test
    void wijzigAsset() {
    }
}