// Created by S.C. van Gils
// Creation date 3-12-2021

package com.example.thevault.service;

import com.example.thevault.domain.model.Klant;
import com.example.thevault.domain.transfer.KlantDto;
import com.example.thevault.support.exceptions.IncorrectFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Facade {

    private final Logger logger = LoggerFactory.getLogger(Facade.class);

    private final KlantService klantService;
    private final AdresService adresService;
    private final RekeningService rekeningService;

    @Autowired
    public Facade(KlantService klantService, AdresService adresService, RekeningService rekeningService) {
        super();
        this.klantService =  klantService;
        this.adresService = adresService;
        this.rekeningService = rekeningService;
        logger.info("New Facade");
    }
    public KlantDto registreerKlant(Klant klant){
/*      if(!adresService.postcodeOpmaak(klant.getAdres().getPostcode())){
          throw new IncorrectFormatException();
      }*/
        klantService.registreerKlant(klant);
        klant.setRekening(rekeningService.creeerRekening(klant));
        return new KlantDto(klant);
    }

}
