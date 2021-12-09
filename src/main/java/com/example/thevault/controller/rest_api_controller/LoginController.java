// Created by E.S. Olthof
// Creation date 08-12-2021
package com.example.thevault.controller.rest_api_controller;

import com.example.thevault.domain.model.Klant;
import com.example.thevault.domain.transfer.LoginDto;
import com.example.thevault.domain.transfer.WelkomDTO;
import com.example.thevault.service.KlantService;
import com.example.thevault.service.LoginService;
import com.example.thevault.service.RegistrationService;
import com.example.thevault.support.authorization.AuthorizationService;
import com.example.thevault.support.authorization.TokenKlantCombinatie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.security.auth.login.LoginException;

public class LoginController extends BasisApiController {

    private final Logger logger = LoggerFactory.getLogger(KlantController.class);

    public LoginController (RegistrationService registrationService, AuthorizationService authorizationService, LoginService loginService) {
        super(registrationService, authorizationService, loginService);
        logger.info("New KlantController");
    }

    @PostMapping("/login")
    public ResponseEntity<WelkomDTO> loginHandler(@RequestBody LoginDto loginDto) throws LoginException {
        //roep loginValidatie aan in de service
        Klant klant = loginService.valideerLogin(loginDto);
        if(klant != null){
            // haal opaak token op uit database
            TokenKlantCombinatie tokenKlantCombinatie = authorizationService.authoriseerKlantMetOpaakToken(klant);
            // genereer cookie met het opgehaalde opaakToken
            ResponseCookie responseCookie = ResponseCookie.from("Refresh Token",
                    tokenKlantCombinatie.toString()).httpOnly(true).build();
            // genereer JWT token
            String jwtToken = authorizationService.generateJwtToken(klant);
            // hier moeten de tokens worden toegevoegd aan de header
            return ResponseEntity.ok()
                    .header("Authorization", jwtToken) // het JWT token ZONDER bearer geeft later problemen
                    .header(HttpHeaders.SET_COOKIE, responseCookie.toString()) // cookie met de refreshtoken
                    .body(new WelkomDTO(klant)); //geeft een welkom DTO terug met Saldo, iban en portefeuille van de klant
        }
        throw new LoginException();
    }

}
