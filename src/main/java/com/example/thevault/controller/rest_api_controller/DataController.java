// Created by S.C. van Gils
// Creation date 20-12-2021

package com.example.thevault.controller.rest_api_controller;

import com.example.thevault.domain.model.*;
import com.example.thevault.service.*;
import com.example.thevault.support.BSNvalidator;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
public class DataController extends BasisApiController {

    private final Logger logger = LoggerFactory.getLogger(DataController.class);
    private final AssetService assetService;
    private final CryptoWaardeService cryptoWaardeService;


    public DataController(RegistrationService registrationService,
                          AuthorizationService authorizationService, LoginService loginService, TransactieService transactieService,
                          AssetService assetService, CryptoWaardeService cryptoWaardeService) {
        super(registrationService, authorizationService, loginService, transactieService);
        this.assetService = assetService;
        this.cryptoWaardeService = cryptoWaardeService;
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
    public ResponseEntity<String> vulTransactieDatabase(@RequestBody String hoeveelTransacties) {
        Gebruiker klant = loginService.vindKlantByGebruikersnaam("LavernRoman");
        Gebruiker andereKlant = loginService.vindKlantByGebruikersnaam("ColumbusMccoy");
        Gebruiker bank = Bank.getInstance();
        List<Cryptomunt> cryptomuntList = assetService.geefAlleCryptomunten();
  /*      for(Cryptomunt cryptomunt: cryptomuntList){
            Asset asset = new Asset(cryptomunt, 100000);
            asset.setGebruiker(bank);
            assetService.slaNieuwAssetOp(asset);
        }*/
        assetService.vulPortefeuilleVanGebruiker(bank);
     //   cryptoWaardeService.haalCryptoWaardes();
        Cryptomunt bitcoin = cryptomuntList.get(0);
        CryptoWaarde bitcoinWaarde = cryptoWaardeService.vindCryptoWaarde(bitcoin);
        transactieService.sluitTransactie(bank, bitcoin, bitcoinWaarde.getWaarde(), bitcoinWaarde.getWaarde(), 0.001, klant);
        return null;
    }
}
