// Elise Olthof
// 20210912

package com.example.thevault.support.authorization;

import com.auth0.jwt.JWT;


import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.thevault.domain.mapping.repository.RootRepository;
import com.example.thevault.domain.model.Klant;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;


class AuthorizationServiceTest {

    public Klant testKlant;
    public String testVerlopenAccessToken;
    public UUID testRefreshToken;
    public String secret_key = "secretTheVaultKey5dRQPD_sCsArU";
    public RootRepository mockRepo;
    public TokenKlantCombinatieDao mockTokenKlantCombinatieDao;
    public AuthorizationService authorizationServiceTest = new AuthorizationService(mockTokenKlantCombinatieDao);
    private int negatieveVerlooptijd = -1;
    String testAccessTokenFromService;


    @JsonIgnore
    private final Logger logger = LoggerFactory.getLogger(AuthorizationService.class);


    @BeforeEach
    public void setup(){
        testKlant = new Klant( "Henknr1", "Welkom01", "Henk", 1890393, LocalDate.of(1991, 1, 12));
        mockRepo = Mockito.mock(RootRepository.class);
        mockTokenKlantCombinatieDao = Mockito.mock(TokenKlantCombinatieDao.class);
        authorizationServiceTest = new AuthorizationService(mockTokenKlantCombinatieDao);
        testAccessTokenFromService = authorizationServiceTest.genereerAccessToken(testKlant);
        setTestVerlopenAccessToken();
        testRefreshToken = UUID.randomUUID();
    }

    /**
     * Methode om een testToken aan te maken waarvan de
     * tijd verstreken is, deze wordt in de methode
     * testVerlopenAccesToken aageropen
     *
     * @return String verlopenAccessToken
     * */
    public void setTestVerlopenAccessToken (){
        // Genereer JWT token met verlopen tijd
        Algorithm algorithm = Algorithm.HMAC256(secret_key);

        Instant gemaaktOp = Instant.now().truncatedTo(ChronoUnit.SECONDS);
        Instant verlooptOp = gemaaktOp.plus(negatieveVerlooptijd, ChronoUnit.MINUTES);

        testVerlopenAccessToken = JWT.create()
                .withSubject(testKlant.getGebruikersnaam())
                .withIssuedAt(Date.from(gemaaktOp))
                .withExpiresAt(Date.from(verlooptOp))
                .withIssuer("TVLT")
                .sign(algorithm);
    }

    @Test
    void genereerRefreshToken() {
        //TODO is deze test uberhaupt nodig,
        // zo ja, aanpassen naar juiste test
        testRefreshToken = UUID.randomUUID();
        assertThat(testRefreshToken).isNotNull().isInstanceOf(UUID.class);
        logger.info("UUID refresh token notNull en instantie UUID");
    }

    /**
     * De onderstaande test genereert een JWT token, controleert
     * of het token bestaat en of deze de het eerste deel bevat
     * waarin het signature van het algoritme staat en informatie
     * over het type token
     *
     * @return void
     * */
    @Test
    void genereerAccessToken() {
        testAccessTokenFromService = authorizationServiceTest.genereerAccessToken(testKlant);
        DecodedJWT decodedJwt = JWT.decode(testAccessTokenFromService);


        assertThat(testAccessTokenFromService).isNotNull().isInstanceOf(String.class).contains("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9");

        logger.info("testAccessTokenFromService isNotNull en bevat juiste encodering");
    }

    /**
     * !!!TO DO
     *
     * Kijken of de methode genereerAccessToken de juiste exception vangt
     *
     * @return void
     * */
    @Test
    void genereerAccessTokenCatchError() {
        testAccessTokenFromService = authorizationServiceTest.genereerAccessToken(new Klant());
        DecodedJWT decodedJwt = JWT.decode(testAccessTokenFromService);

        assertThat(testAccessTokenFromService).isNotNull().isInstanceOf(String.class).contains("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9");

        logger.info("testAccessTokenFromService isNotNull en bevat juiste encodering");
    }

    /**
     * De onderstaande test genereert een JWT token, controleert
     * of het token bestaat en of deze de het eerste deel bevat
     * waarin het signature van het algoritme staat en informatie
     * over het type token
     *
     * @return void
     * */
    @Test
    void valideerAccessTokenFromAuthorizationService() {
        authorizationServiceTest.valideerAccessToken(testAccessTokenFromService, testKlant);
        // print alle eigenschappen van het acces token
        DecodedJWT decodedJwt = JWT.decode(testAccessTokenFromService);
        printOutComesTokenValidation(decodedJwt);
    }


    /**
     * Test of er een exception wordt gegooid wanneer er
     * een klant aan de validatieMethode wordt meegegeven waarvoor het token
     * niet bedoelt is, speciefieke exception: een InvalidClaimException.
     *
     * Als de valideerAccessToken methode een false teruggeeft is de test geslaagd
     *
     **/
    @Test
    void valideerAccessTokenMetVerkeerdeKlant() {
            boolean returnedBooleanValue = authorizationServiceTest.valideerAccessToken(testAccessTokenFromService, new Klant( "harrynr1", "Welkom01", "Henk", 1890393, LocalDate.of(1991, 1, 12)));
            // print alle eigenschappen van het token
            DecodedJWT decodedJwt = JWT.decode(testAccessTokenFromService);
            printOutComesTokenValidation(decodedJwt);
            // als de boolean false is is de test geslaagd
            if(!returnedBooleanValue){
                System.out.println("test geslaagd");
            } else {
                fail("Moet een JWTVerificationException gooien met InvalidClaimException");
            }
    }

    /**
     * Test of de methode valideerAccessToken bij een verlopen token
     * een JWTVerificationException opgooit. De valideerAccessToken methode
     * moet dus een fail teruggeven.
     *
     * specifieke exception: TokenExpiredException
     *
     * @return void
     * */
    @Test
    void valideerAccessTokenVerlopen(){
        boolean outcomeValidatie = authorizationServiceTest.valideerAccessToken(testVerlopenAccessToken, testKlant);
        // print alle eigenschappen van het token
        DecodedJWT decodedJwt = JWT.decode(testVerlopenAccessToken);
        printOutComesTokenValidation(decodedJwt);
        if(!outcomeValidatie){
            System.out.println("test geslaagd");
        } else{
            fail("Moet een JWTVerificationException gooien");
        }
    }

    /**
     * Methode om alle eigenschappen van een meegegeven DecodedJWT
     * te kunnen printen
     *
     * @return void
     * */
    public void printOutComesTokenValidation (DecodedJWT decodedAccesToken) {
        System.out.println("Header =  " + decodedAccesToken.getHeader());
        System.out.println("Algorithm =  " + decodedAccesToken.getAlgorithm());
        System.out.println("Audience =  " + decodedAccesToken.getAudience());
        decodedAccesToken.getClaims().forEach((k, v) -> {
            System.out.println("Claim " + k + " = " + v.asString());
        });
        System.out.println("ContentType =  " + decodedAccesToken.getContentType());
        System.out.println("ExpiresAt =  " + decodedAccesToken.getExpiresAt());
        System.out.println("Id =  " + decodedAccesToken.getId());
        System.out.println("Issuer =  " + decodedAccesToken.getIssuer());
        System.out.println("Subject =  " + decodedAccesToken.getSubject());
    }

    /**
     * Methode om de authorisatie van de klant het refresh token te testen
     *
     * TO DO moet nog verder uitgewerkt worden met database test om te zien of de
     * database de combinatie goed opslaat, delete en vervangt. H2-database nodig.
     *
     * @return void
     * */
    @Test
    void authoriseerKlantMetRefreshToken() {
        Klant nieuweKlant = new Klant("TimoBaasnummer1", "DuinDeBeste01", "Timo", 1890393, LocalDate.of(1992, 1, 10));
        TokenKlantCombinatie startCombinatie = authorizationServiceTest.authoriseerKlantMetRefreshToken(testKlant);
        TokenKlantCombinatie actualCombinatie = authorizationServiceTest.authoriseerKlantMetRefreshToken(testKlant);

        // sla nieuwe klant op
        TokenKlantCombinatie testCombinatieNieuweKlant = authorizationServiceTest.authoriseerKlantMetRefreshToken(nieuweKlant);
        assertThat(testCombinatieNieuweKlant.getKlant().getNaam().equals("Timo"));
        assertThat(testCombinatieNieuweKlant.getKey()).isNotNull().isInstanceOf(UUID.class);
        logger.info("testCombinatie nieuwe klant = " + testCombinatieNieuweKlant);

        // check of de key een UUID random gegenereerde token is
        assertThat(actualCombinatie.getKey()).isInstanceOf(UUID.class).isNotEqualTo(startCombinatie.getKey());

        // check of bij de tweede check de token veranderd is
        assertThat(startCombinatie).isNotEqualTo(actualCombinatie);

    }
}