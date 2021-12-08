package com.example.thevault.service;

import com.example.thevault.domain.mapping.repository.RootRepository;
import com.example.thevault.domain.model.Asset;
import com.example.thevault.domain.model.Cryptomunt;
import com.example.thevault.domain.model.Klant;
import com.example.thevault.domain.transfer.AssetDto;
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

    public static Asset testAsset1;
    public static Asset testAsset2;
    public static Asset testAsset3;
    public static Asset testAsset4;
    public static Asset testAsset5;
    public static AssetDto testAssetDto1;
    public static AssetDto testAssetDto2;
    public static AssetDto testAssetDto3;
    public static AssetDto testAssetDto4;
    public static RootRepository rootRepository;
    public static AssetService assetService;
    public static List<Asset> portefeuille;
    public static List<AssetDto> portefeuilleDto;
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
        testAssetDto1 = new AssetDto(testAsset1);
        testAssetDto2 = new AssetDto(testAsset2);
        testAssetDto3 = new AssetDto(testAsset3);
        testAssetDto4 = new AssetDto(testAsset4);
        rootRepository = Mockito.mock(RootRepository.class);
        assetService = new AssetService(rootRepository);
        portefeuille = new ArrayList<>();
        portefeuille.add(testAsset1);
        portefeuille.add(testAsset2);
        portefeuille.add(testAsset3);
        portefeuilleDto = new ArrayList<>();
        portefeuilleDto.add(testAssetDto1);
        portefeuilleDto.add(testAssetDto2);
        portefeuilleDto.add(testAssetDto3);
    }

    @Test
    void geefInhoudPortefeuille() {
        Mockito.when(rootRepository.vulPortefeuilleKlant(1)).thenReturn(portefeuille);
        List<AssetDto> expected = portefeuilleDto;
        List<AssetDto> actual = assetService.geefInhoudPortefeuille(1);
        assertThat(actual).as("Test geef inhoud portefeuilleDto van testklant").isNotNull().isEqualTo(expected).
                contains(testAssetDto1, atIndex(0)).contains(testAssetDto2, atIndex(1)).contains(testAssetDto3, atIndex(2)).
                doesNotContain(testAssetDto4).hasSize(3).extracting(AssetDto::getName).
                contains("CarmenCrypto", "DigiCrypto", "Coyne").doesNotContain("BitCoin");
    }

    @Test
    void geefCryptomunt() {
        Mockito.when(rootRepository.geefAssetVanKlant(1, 1)).thenReturn(testAsset1);
        AssetDto expected = new AssetDto(testAsset1);
        AssetDto actual = assetService.geefCryptomunt(1, 1);
        assertThat(actual).as("Test geef specifieke AssetDto van testklant").isNotNull().isEqualTo(expected).
                isIn(portefeuilleDto).isNotEqualTo(testAssetDto2).isNotSameAs(testAssetDto3);
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