// Created by S.C. van Gils
// Creation date 1-12-2021

package com.example.thevault.controller.rest_api_controller;

import com.example.thevault.domain.model.Adres;
import com.example.thevault.domain.model.Klant;
import com.example.thevault.domain.model.PostModel;
import com.example.thevault.domain.transfer.RegistrationDto;
import com.example.thevault.domain.transfer.WelkomDTO;
import com.example.thevault.service.LoginService;
import com.example.thevault.service.RegistrationService;
import com.example.thevault.domain.transfer.LoginDto;
import com.example.thevault.service.KlantService;
import com.example.thevault.support.authorization.AuthorizationService;
import com.example.thevault.support.authorization.TokenKlantCombinatie;
import com.example.thevault.support.exceptions.LoginFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.login.LoginException;

@RestController
public class KlantController extends BasisApiController{

    private final Logger logger = LoggerFactory.getLogger(KlantController.class);

    public KlantController(RegistrationService registrationService,
                           AuthorizationService authorizationService, LoginService loginService) {
        super(registrationService, authorizationService, loginService);
        logger.info("New KlantController");
    }

    /**
     *  Deze methode zorgt ervoor dat ingevoerde registratiegegevens in een Klant-object
     *  worden omgezet, waarna het registratieproces in gang wordt gezet.
     *  Als alles goed gaat, krijgt de klant de voor hem relevante informatie,
     *  met daarin zijn nieuwe rekeningnummer, terug alsmede een HTTP-respons 201 = created.
     *
     * @param klant een Klant-object dat wordt aangemaakt op basis van ingevoerde gegevens
     * @return een DTO waar de relevante klantgegevens in staan als de klant succesvol is opgeslagen
     */
    @PostMapping("/register")
    public ResponseEntity<RegistrationDto> registreerKlantHandler(@RequestBody Klant klant){
        System.out.println(klant);
        RegistrationDto registrationDto = registrationService.registreerKlant(klant);
    return ResponseEntity.status(HttpStatus.CREATED).body(registrationDto);
    }
    @PostMapping("/klantDashboard") // 401 response nodig indien token niet geldig
    public ResponseEntity<String> clientDashboardHandler(@RequestHeader("Authorization") String token, @RequestBody String inhoud){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(inhoud);
    }
    @PostMapping("/test")
    public ResponseEntity<Klant> testRegistratie(@RequestBody Klant klant){
        return ResponseEntity.status(HttpStatus.CREATED).body(klant);
    }


    /**
    @author Wim 20211207
    methode die inloggegevens ontvangt en laat checken of deze correct zijn en inlog succesvol wordt
    of een algemene foutmelding verstuurt
     */
/*    @PostMapping("/login")
    public ResponseEntity<WelkomDTO> loginHandler(@RequestBody LoginDto loginDto) throws LoginFailedException {
        //roep loginValidatie aan in de service
        Klant klant = loginService.valideerLogin(loginDto);
        System.out.println();
        System.out.println(klant);
        System.out.println();
        if(klant != null){
            TokenKlantCombinatie tokenKlantCombinatie = authorizationService.authoriseer(klant);
            System.out.println();
            System.out.println(tokenKlantCombinatie.toString());
            String jwtToken = authorizationService.generateJwtToken(klant);
            System.out.println("test van transparent: " + jwtToken);
            System.out.println();
            // hier moeten de tokens worden toegevoegd aan de header
            return ResponseEntity.ok()
                    .header("Authorization", tokenKlantCombinatie.getKey().toString(), "AuthoriatJwt", jwtToken)
                    .body(new WelkomDTO(klant));
        }
        throw new LoginFailedException();
    }*/

    @PostMapping("/login")
    public ResponseEntity<WelkomDTO> loginHandler(@RequestBody LoginDto loginDto) throws LoginFailedException {
        //roep loginValidatie aan in de service
        Klant klant = loginService.valideerLogin(loginDto);
        if(klant != null){
            // haal opaak token op uit database
            TokenKlantCombinatie tokenKlantCombinatie = authorizationService.authoriseerKlantMetRefreshToken(klant);
            // genereer cookie met het opgehaalde opaakToken
            ResponseCookie responseCookie = ResponseCookie.from("RefreshToken",
                    tokenKlantCombinatie.getKey().toString()).httpOnly(true).build();
            // genereer JWT token
            String jwtToken = authorizationService.genereerAccessToken(klant);
            // hier moeten de tokens worden toegevoegd aan de header
            return ResponseEntity.ok()
                    .header("Authorization", "Bearer " + jwtToken)
                    .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                    .body(new WelkomDTO(klant));
        }
        throw new LoginFailedException();
    }

}
