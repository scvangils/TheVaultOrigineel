package com.example.thevault.domain.mapping.dao;

import com.example.thevault.domain.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.doCallRealMethod;

@SpringBootTest
@ActiveProfiles("test")
class JDBCAssetDAOTest {

    private AssetDAO testAssetDAO;
    @MockBean
    private AssetDAO mockAssetDAO;
    private static Gebruiker testKlant1;
    private static Gebruiker testKlant2;
    private static Cryptomunt testCryptomunt1;
    private static Cryptomunt testCryptomunt2;
    private static Cryptomunt testCryptomunt3;
    private static Asset testAsset1;
    private static Asset testAsset2;
    private static Asset testAsset3;
    private static Asset testAsset4;
    private static Asset testAsset5;
    private static List<Asset> testAsset6;

    private static Gebruiker mockKlant;
    private Cryptomunt mockCrypto;

    @Autowired
    public JDBCAssetDAOTest(AssetDAO testAssetDAO) {
        super();
        this.testAssetDAO = testAssetDAO;
    }

    @Test
    public void setupTest(){
        assertThat(testAssetDAO).isNotNull();
    }

    @BeforeEach
    void setup() {
        testKlant1 = new Klant("Jolien", "BeterWachtwoord",
                null, null, null, "Jolien",null,
                987654321, LocalDate.parse("1985-10-14"));
        testKlant2 = new Klant("Carmen", "GoedWachtwoord",
                null, null, null,"Carmen", null,
                123456789, LocalDate.parse("1985-12-30"));
        testKlant1.setGebruikerId(2);
        testKlant2.setGebruikerId(1);
        testCryptomunt1 = new Cryptomunt(1, "Bitcoin", "BCN");
        testCryptomunt2 = new Cryptomunt(1, null, null);
        testCryptomunt3 = new Cryptomunt(2, null, null);
        testAsset1 = new Asset(testCryptomunt1, 3.4, testKlant1);
        testAsset2 = new Asset(testCryptomunt1, 4.2, testKlant2);
        testAsset3 = new Asset(testCryptomunt2, 5.3, testKlant2);
        testAsset4 = new Asset(testCryptomunt2, 4.2, testKlant2);
        testAsset5 = new Asset(testCryptomunt3, 3.5, testKlant2);
        testAsset6 = new ArrayList<>();
        testAsset6.add(testAsset4);
        testAsset6.add(testAsset5);

        mockKlant = Mockito.mock(Gebruiker.class);
        mockCrypto = Mockito.mock(Cryptomunt.class);
    }

    @Test
    void voegNieuwAssetToeAanPortefeuille() {
        Asset actual = testAssetDAO.voegNieuwAssetToeAanPortefeuille(testAsset1);
        Asset expected = testAsset1;
        assertThat(actual).as("Test nieuwe asset toevoegen").isNotNull().isEqualTo(expected).extracting(Asset::getGebruiker).
                extracting(Gebruiker::getGebruikersnaam).isEqualTo("Jolien");
    }

    @Test
    void verwijderAssetUitPortefeuille() {
        Asset actual = testAssetDAO.verwijderAssetUitPortefeuille(testAsset2);
        Asset expected = testAsset2;
        assertThat(actual).as("Verwijder asset uit portefeuille test deel 1").isNotNull().isEqualTo(expected).
                hasNoNullFieldsOrPropertiesExcept("Cryptomunt", "Aantal", "Gebruiker");
        Optional<Asset> actual2 = testAssetDAO.geefAssetGebruiker(testKlant2, testCryptomunt1);
        assertThat(actual2).as("Verwijder asset uit portefeuille test deel 2").isEmpty();
    }

    //TODO Carmen: Uitvinden hoe ik een integratietest kan maken voor deze method, gevraagd aan Huub
    /*@Test
    void updateAssetAankoop() {
        doCallRealMethod().when(mockAssetDAO).updateAsset(testKlant2, testCryptomunt1, 1.1);
        Mockito.when(mockAssetDAO.geefAssetGebruiker(testKlant2, testCryptomunt1)).
                thenReturn(Optional.ofNullable(testAsset2));
        Asset actual = mockAssetDAO.updateAsset(testKlant2, testCryptomunt1, 1.1);
        Asset expected = testAsset3;
        assertThat(actual).as("Test asset aankoop").isNotNull().isEqualTo(expected);

        doCallRealMethod().when(mockAssetDAO).updateAsset(mockKlant, mockCrypto, 1.1);
        Mockito.when(mockAssetDAO.geefAssetGebruiker(mockKlant, mockCrypto)).
                thenReturn(Optional.ofNullable(testAsset2));
        Asset actual = mockAssetDAO.updateAsset(mockKlant, mockCrypto, 1.1);
        Asset expected = testAsset3;
        assertThat(actual).as("Test asset aankoop").isNotNull().isEqualTo(expected);
    }

    @Test
    void updateAssetVerkoop() {
        Asset actual = testAssetDAO.updateAsset(testKlant2, testCryptomunt1, 1);
    }

    @Test
    void updateAssetVerkoopTekortAanCrypto() {
        Asset actual = testAssetDAO.updateAsset(testKlant2, testCryptomunt1, 1);
    }
*/
    @Test
    void geefAanwezigAsset() {
        Optional<Asset> actual = testAssetDAO.geefAssetGebruiker(testKlant2, testCryptomunt1);
        Asset expected = testAsset4;
        assertThat(actual).as("Test geef 1 asset van gebruiker").isNotEmpty().contains(expected);
    }

    @Test
    void geefNietAanwezigAsset() {
        Optional<Asset> actual = testAssetDAO.geefAssetGebruiker(testKlant1, testCryptomunt2);
        assertThat(actual).as("Test geef niet bestaand asset van gebruiker").isEmpty();
    }

    @Test
    void geefAlleAssets() {
        List<Asset> actual = testAssetDAO.geefAlleAssets(testKlant2);
        List<Asset> expected = testAsset6;
        assertThat(actual).as("Test ophalen alle assets van gebruiker").isNotNull().contains(expected.get(0)).
                contains(expected.get(1));
    }

    @Test
    void geefAlleAssetsLeeg() {
        List<Asset> actual = testAssetDAO.geefAlleAssets(testKlant1);
        assertThat(actual).as("Test ophalen alle assets van gebruiker is leeg").isNullOrEmpty();
    }

    /*@AfterEach
    void breakdown() {
        AssetDAO testAssetDAO = null;
        Gebruiker testKlant1 = null;
        Gebruiker testKlant2 = null;
        Cryptomunt testCryptomunt1 = null;
        Cryptomunt testCryptomunt2 = null;
        Cryptomunt testCryptomunt3 = null;
        Asset testAsset1 = null;
        Asset testAsset2 = null;
        Asset testAsset3 = null;
        Asset testAsset4 = null;
        Asset testAsset5 = null;
        List<Asset> testAsset6 = null;
    }*/
}