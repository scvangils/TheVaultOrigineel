package com.example.thevault.service;

import com.example.thevault.domain.model.IBAN;
import org.iban4j.CountryCode;
import org.iban4j.Iban;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

//import static org.junit.jupiter.api.Assertions.*;

class RekeningServiceTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void createIban() {
        Iban iban = new Iban.Builder().countryCode(CountryCode.NL).bankCode("TVLT").buildRandom();
        assertThat(iban.toString()).contains("NL");
    }
}