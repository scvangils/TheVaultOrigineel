// Created by S.C. van Gils
// Creation date 1-12-2021

package com.example.thevault.controller.rest_api_controller;

import com.example.thevault.domain.model.Klant;
import com.example.thevault.domain.model.PostModel;
import com.example.thevault.service.KlantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KlantController {

    private final Logger logger = LoggerFactory.getLogger(KlantController.class);

    private KlantService klantService;

    public KlantController(KlantService klantService) {
        super();
        this.klantService = klantService;
        logger.info("New KlantController");
    }
@PostMapping("/register")
    public ResponseEntity<Klant> registreerKlantHandler(@RequestBody Klant klant){
       klant = klantService.registreerKlant(klant);
    return ResponseEntity.status(201).body(klant);
}

}
