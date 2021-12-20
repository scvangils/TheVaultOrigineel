// Created by S.C. van Gils
// Creation date 20-12-2021

package com.example.thevault.controller.rest_api_controller;

import com.example.thevault.domain.model.Klant;
import com.example.thevault.service.LoginService;
import com.example.thevault.service.RegistrationService;
import com.example.thevault.support.authorization.AuthorizationService;
import com.example.thevault.support.data.DataGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class DataController extends BasisApiController {

    private final Logger logger = LoggerFactory.getLogger(DataController.class);

    public DataController(RegistrationService registrationService,
                          AuthorizationService authorizationService, LoginService loginService) {
        super(registrationService, authorizationService, loginService);
        logger.info("New DataController");
    }

    @PostMapping("/klantdata")
    public ResponseEntity<String> vulKlantAdresEnRekeningTabel(@RequestBody String hoeveelKlanten) throws IOException {
        List<Klant> list = DataGenerator.maakLijstKlantenVanCSV("Sprint2/datacsv.csv", Integer.parseInt(hoeveelKlanten));
        int count = 0;
        for (Klant klant : list) {
            registrationService.registreerKlant(klant);
            count++;
        }
        String bericht = String.format("Zoveel geïmporteerd: %d", count);
        return ResponseEntity.status(HttpStatus.OK).body(bericht);
    }

    @PostMapping("/transactiedata")
    public ResponseEntity<String> vulTransactieDatabase(@RequestBody int hoeveelTransacties) {
        return null;
    }
}
