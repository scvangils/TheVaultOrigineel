// Created by E.S. Olthof
// Creation date 21/12/2021


package com.example.thevault.domain.mapping.dao;

import com.example.thevault.domain.model.Klant;
import com.example.thevault.domain.model.Transactie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class JDBCTransactieDAOTest {


    private Klant testKlant1;
    private Klant testKlant2;
    private Transactie testTransactie1;
    private Transactie getTestTransactie2;


    @BeforeEach
    void setup(){
        testKlant1 = new Klant("Carmen", "GoedWachtwoord",
                null, null, null,"Carmen", null, 123456789, LocalDate.parse("1985-12-30"));
        testKlant2 = new Klant("Jolien", "BeterWachtwoord",
                null, null, null, "Jolien",null, 987654321, LocalDate.parse("1985-10-14"));



    }


    @Test
    void slaTransactieOp() {
    }

    @Test
    void geefAlleTransacties() {
    }

    @Test
    void geefTransactiesVanGebruiker() {
    }

    @Test
    void geefTransactiesVanGebruikerInPeriode() {
    }

    @Test
    void geefAlleTransactiesInPeriode() {
    }

    @Test
    void geefTransactiesVanGebruikerMetCryptomunt() {
    }
}
