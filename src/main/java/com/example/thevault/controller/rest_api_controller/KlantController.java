// Created by S.C. van Gils
// Creation date 1-12-2021

package com.example.thevault.controller.rest_api_controller;

import com.example.thevault.domain.model.Klant;
import com.example.thevault.domain.transfer.KlantDto;
import com.example.thevault.service.Facade;
import com.example.thevault.service.KlantService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KlantController extends BasisApiController{

    private final Logger logger = LoggerFactory.getLogger(KlantController.class);

    public KlantController(Facade facade, KlantService klantService) {
        super(facade, klantService);
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
    public ResponseEntity<KlantDto> registreerKlantHandler(@RequestBody Klant klant){
        KlantDto klantDto = facade.registreerKlant(klant);
    return ResponseEntity.status(HttpStatus.CREATED).body(klantDto);
    }
}
