// Created by S.C. van Gils
// Creation date 20-12-2021

package com.example.thevault.domain.mapping.dao;

import com.example.thevault.domain.model.Adres;
import com.example.thevault.domain.model.Gebruiker;
import com.example.thevault.domain.model.Klant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
public class JDBCKlantDAOTest {

    private JDBCKlantDAO jdbcKlantDAOTest;
    private static Gebruiker testKlant1;
    private static Gebruiker testKlant2;
    private static Gebruiker testKlant3;
    private static Adres testAdres1;
    private static Klant nieuweKlant;

    @Autowired
    public JDBCKlantDAOTest(JDBCKlantDAO jdbcKlantDAO) {
        super();
        this.jdbcKlantDAOTest = jdbcKlantDAO;
    }


 @Test
 void testTheTest(){
       assertThat(jdbcKlantDAOTest).isNotNull();
 }
 @BeforeEach
 void setup(){
        testKlant1 = new Klant("Carmen", "GoedWachtwoord",
                null, null, null,"Carmen", null, 123456789, LocalDate.parse("1985-12-30"));
        testKlant2 = new Klant("Jolien", "BeterWachtwoord",
                null, null, null, "Jolien",null, 987654321, LocalDate.parse("1985-10-14"));
        testKlant3 = new Klant("Steven", "mijnWachtwoord",
                null, null, null,"Steven", null, 123456789, LocalDate.parse("1975-12-30"));
        testAdres1 = new Adres("Hoofdstraat", 4, null, "1234AB", "Hellevoetsluis");
        testAdres1.setAdresId(1);
        nieuweKlant = new Klant("StevenVG", "StevenPW", "Steven van Gils", 1010101010, LocalDate.parse("1975-12-30"));

 }


 @Test
 void vindKlantById(){
     Gebruiker actual = jdbcKlantDAOTest.vindKlantById(1);
     Gebruiker expected = testKlant1;
     assertThat(actual).as("GebruikerId wordt niet meegenomen in de equals methode, ze moeten hetzelfde zijn").isNotNull().isEqualTo(expected);
     ((Klant) expected).setBsn(0);
     assertThat(actual).as("BSN aangepast, horen niet meer gelijk te zijn").isNotNull().isNotEqualTo(expected);
 }

 @Test
 void slaNieuweKlantOp(){
        nieuweKlant.setAdres(testAdres1);
        Gebruiker actual = jdbcKlantDAOTest.slaKlantOp(nieuweKlant);
        nieuweKlant.setGebruikerId(3);
        Gebruiker expected = nieuweKlant;
        assertThat(actual).as("gebruikerId moet zijn aangepast").isNotNull().isEqualTo(expected);
 }
    @Test
    void slaKlantMetBestaandeUsernameOp() {
        Klant expected = (Klant) testKlant1;
        ((Klant) testKlant1).setAdres(testAdres1);
        assertThatThrownBy(() -> jdbcKlantDAOTest.slaKlantOp(expected)).isInstanceOf(DuplicateKeyException.class);
    }

    @Test
    void vindAlleKlanten() {
        List<Klant> actual = jdbcKlantDAOTest.vindAlleKlanten();
        List<Klant> expected = new ArrayList<>();
        expected.add((Klant) testKlant1);
        expected.add((Klant) testKlant2);
        assertThat(actual).as("Alle klanten in de database").isNotNull().isEqualTo(expected);
    }

    @Test
    void vindKlantByGebruikersnaam() {
        Gebruiker actual = jdbcKlantDAOTest.vindKlantByGebruikersnaam("Jolien");
        Gebruiker expected = testKlant2;
        assertThat(actual).as("Ze moeten hetzelfde zijn").isNotNull().isEqualTo(expected);
        expected.setGebruikersnaam("Jolie");
        assertThat(actual).as("Gebruikersnaam aangepast, horen niet meer gelijk te zijn").isNotNull().isNotEqualTo(expected);
    }
    @Test
    void vindNietBestaandeKlantByGebruikersnaam(){
        Gebruiker actual = jdbcKlantDAOTest.vindKlantByGebruikersnaam("Steven");
        Gebruiker expected = null;
        assertThat(actual).as("Deze klant is niet geregistreerd.").isNull();
    }

    @Test
    void verwijderKlant() {
        testKlant1.setGebruikerId(1);
       Gebruiker actual = jdbcKlantDAOTest.verwijderKlant((Klant) testKlant1);
       Gebruiker expected = testKlant1;
        assertThat(actual).as("Ze moeten hetzelfde zijn").isNotNull().isEqualTo(expected);
        actual = jdbcKlantDAOTest.vindKlantByGebruikersnaam(testKlant1.getGebruikersnaam());
        expected = null;
        assertThat(actual).as("Deze klant is niet geregistreerd.").isNull();
    }

    @Test
    void updateKlant() {

    }
}
