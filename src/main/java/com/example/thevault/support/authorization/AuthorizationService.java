// Created by carme
// Creation date 01/12/2021

package com.example.thevault.support.authorization;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.thevault.domain.model.Klant;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthorizationService {
    private final static String PRIVATE_KEY = "private_key";
    private static TokenKlantCombinatieDao tokenKlantCombinatieDao;
    //protected final TokenKlantCombinatieDao tokenKlantCombinatieDao;

    @JsonIgnore
    private final Logger logger = LoggerFactory.getLogger(AuthorizationService.class);

    @Autowired
    public AuthorizationService(TokenKlantCombinatieDao tokenKlantCombinatieDao) {
        super();
        this.tokenKlantCombinatieDao = tokenKlantCombinatieDao;
        logger.info("New AuthorizationSupport");
    }
    /**== TO DO:
     -- encryption cookie
     == welke eigenschappen moet de cookie meekrijgen
     == Verify token method
     */
    public UUID getKey() {
        UUID opaakToken = UUID.randomUUID();
        logger.info("Nieuw opaakToken opgehaald: {}", opaakToken);
        return opaakToken;

    }

    //jwt token met
    public String generateJwtToken (Klant klant){
        String jwtToken = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256(PRIVATE_KEY);
            // Optie?: Algorithm algorithm = Algorithm.HMAC256(tokenKlantCombinatieDao.vindTokenKlantCombinatieMetKlant(klant).get().getKey().toString());

            Instant gemaaktOp = Instant.now().truncatedTo(ChronoUnit.SECONDS);
            Instant verlooptOp = gemaaktOp.plus(20, ChronoUnit.MINUTES);

            logger.info("Gecreeerd op: {}", gemaaktOp);
            logger.info("Veerloopt op: {}", verlooptOp);

            jwtToken = JWT.create()
                    .withSubject(klant.getGebruikersnaam())
                    .withIssuedAt(Date.from(gemaaktOp))
                    .withExpiresAt(Date.from(verlooptOp))
                    .withIssuer("auth0")
                    .sign(algorithm);

            /* NOG UITZOEKEN:
            Claims claims = Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(jws)
                    .getBody();*/

        } catch (JWTCreationException exception){
            logger.info("Invalid Signing configuration / Couldn't convert Claims.", exception);
        }
        logger.info("Token (HMAC256) gemaakt: {}", jwtToken);
        return jwtToken;
    }

        // Valideer token
        // Als het dus false returned moet er een exception gegooid worden
        // wat moet deze valideer methode teruggeven?
    public boolean valideerJwtToken(String jwtToken) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(getKey().toString());
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("auth0")
                    .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(jwtToken);
            // waarvoor ookalweer require? > : JWT.require(Algorithm.HMAC256(PRIVATE_KEY));
            logger.info("Token gevalideerd");
        } catch (JWTVerificationException exception) {
            logger.info("JWTtoken niet gevalideerd");
            return false;
        }
        return true;
    }

    //opaak token combineren met klant in token-database
    public TokenKlantCombinatie authoriseerKlantMetOpaakToken(Klant klant) {
        Optional<TokenKlantCombinatie> optioneleCombinatie = tokenKlantCombinatieDao.vindTokenKlantCombinatieMetKlant(klant);
        if (optioneleCombinatie.isPresent()) {
            tokenKlantCombinatieDao.delete(optioneleCombinatie.get().getKey());
        }
        UUID opaakToken = getKey();
        TokenKlantCombinatie tokenMemberPair = new TokenKlantCombinatie(opaakToken, klant);
        tokenKlantCombinatieDao.slaTokenKlantPairOp(tokenMemberPair);
        return tokenMemberPair;
    }

    public static void main(String[] args) {
        AuthorizationService authorizationSupport = new AuthorizationService(tokenKlantCombinatieDao);
        Klant testKlant = new Klant( "Henknr1", "fdsaljkl", "Hello", 1890393, LocalDate.of(1991, 1, 12));
        authorizationSupport.getKey();
        String token = authorizationSupport.generateJwtToken(testKlant);
        authorizationSupport.valideerJwtToken(token);
    }
}
