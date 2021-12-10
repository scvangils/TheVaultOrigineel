// Elise Olthof
// 20210912

package com.example.thevault.support.authorization;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class AuthorizationServiceTest {

public UUID opaakToken;

    @Test
    void creeerOpaakToken() {
        UUID opaakToken = UUID.randomUUID();
        assertThat(opaakToken);
    }

    @Test
    void generateJwtToken() {
    }

    @Test
    void valideerJwtToken() {

    }

    @Test
    void authoriseerKlantMetOpaakToken() {

    }
}