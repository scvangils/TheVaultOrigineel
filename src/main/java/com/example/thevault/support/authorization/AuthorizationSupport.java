// Created by carme
// Creation date 01/12/2021

package com.example.thevault.support.authorization;
import com.example.thevault.domain.model.Klant;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AuthorizationSupport {
    protected final TokenKlantCombinatieDao tokenKlantCombinatieDao;

    @JsonIgnore
    private final Logger logger = LoggerFactory.getLogger(AuthorizationSupport.class);

    @Autowired
    public AuthorizationSupport(TokenKlantCombinatieDao tokenKlantCombinatieDao) {
        super();
        this.tokenKlantCombinatieDao = tokenKlantCombinatieDao;
        logger.info("New AuthorizationSupport");
    }

    public UUID getKey() {
        return UUID.randomUUID();
    }

    public TokenKlantCombinatie authorize(Klant klant) {

        //TO DO:
        /*Optional<TokenKlantCombinatie> optioneleCombinatie = tokenKlantCombinatieDao.vindTokenKlantPairMetKlant(klant);
        if (optioneleCombinatie.isPresent()) {
            tokenKlantCombinatieDao.delete(optioneleCombinatie.get());
        }
        UUID token = UUID.randomUUID();
        TokenKlantCombinatie tokenMemberPair = new TokenKlantCombinatie(token, klant);
        tokenKlantCombinatieDao.slaTokenKlantPairOp(tokenMemberPair);
        return tokenMemberPair;*/
        return null;
    }

    public void main(String[] args) {
        AuthorizationSupport authorizationSupport = new AuthorizationSupport(tokenKlantCombinatieDao);
        System.out.println(authorizationSupport.getKey());
    }
}
