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
import com.example.thevault.support.exceptions.LoginFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.login.LoginException;

@RestController
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
            TokenKlantCombinatie tokenKlantCombinatie = authorizationService.authoriseer(klant);
            ResponseCookie responseCookie = ResponseCookie.from("RefreshToken",
                    tokenKlantCombinatie.getKey().toString()).httpOnly(true).build();
            String jwtToken = authorizationService.generateJwtToken(klant);
            // hier moeten de tokens worden toegevoegd aan de header
            return ResponseEntity.ok()
                    .header("Authorization", "Bearer " + jwtToken)
                    .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                    .body(new WelkomDTO(klant));
        }
        throw new LoginFailedException();
    }

    /*
    @PostMapping("validate")
    public ResponseEntity<String> validationHandler(@RequestHeader String authorization) {
        try {
            UUID uuid = UUID.fromString(authorization);
            Optional<Klant> member = authorizationService.validate(uuid);
            if (member.isPresent()) {
                return ResponseEntity.ok().body(member.get().getFullname());
            } else {
                throw new LoginFailedException();
            }
        } catch (IllegalArgumentException e) {
            throw new LoginFailedException();
        }
    }*/
}
