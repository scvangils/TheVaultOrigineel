// Created by S.C. van Gils
// Creation date 20-12-2021

package com.example.thevault.controller.rest_api_controller;

import com.example.thevault.domain.model.*;
import com.example.thevault.service.*;
import com.example.thevault.support.authorization.AuthorizationService;
import com.example.thevault.support.data.DataGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static com.example.thevault.support.data.DataGenerator.genereerRandomGetal;

//@RestController
//public class DataController extends BasisApiController {
@Component
 public class DataController implements ApplicationListener<ContextRefreshedEvent> {

    private final Logger logger = LoggerFactory.getLogger(DataController.class);
    private final AssetService assetService;
    private final CryptoWaardeService cryptoWaardeService;
    private final AuthorizationService authorizationService;
    private final RegistrationService registrationService;
    private final LoginService loginService;
    private final TransactieService transactieService;
    private final KlantService klantService;


    public DataController(RegistrationService registrationService,
                          AuthorizationService authorizationService, LoginService loginService, TransactieService transactieService,
                          AssetService assetService, CryptoWaardeService cryptoWaardeService, KlantService klantService) {
    //    super(registrationService, authorizationService, loginService, transactieService);
        this.registrationService = registrationService;
        this.authorizationService = authorizationService;
        this.transactieService = transactieService;
        this.loginService = loginService;
        this.assetService = assetService;
        this.cryptoWaardeService = cryptoWaardeService;
        this.klantService = klantService;
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
        List<CryptoWaarde> cryptoWaardeList = haalMeestRecenteCryptoWaardes();
     //   cryptoWaardeService.haalCryptoWaardes();

/*        transactieService.sluitTransactie(bank, bitcoin, bitcoinWaarde.getWaarde(), bitcoinWaarde.getWaarde(), 0.001, klant);*/
        return null;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
/*        Gebruiker klant = loginService.vindKlantByGebruikersnaam("LavernRoman");
        Gebruiker andereKlant = loginService.vindKlantByGebruikersnaam("ColumbusMccoy");
        Gebruiker bank = Bank.getInstance();
        List<Cryptomunt> cryptomuntList = assetService.geefAlleCryptomunten();
  *//*      for(Cryptomunt cryptomunt: cryptomuntList){
            Asset asset = new Asset(cryptomunt, 100000);
            asset.setGebruiker(bank);
            assetService.slaNieuwAssetOp(asset);
        }*//*
        assetService.vulPortefeuilleVanGebruiker(bank);
           cryptoWaardeService.haalCryptoWaardes();
        Cryptomunt bitcoin = cryptomuntList.get(0);
        CryptoWaarde bitcoinWaarde = cryptoWaardeService.vindMeestRecenteCryptoWaarde(bitcoin);*/

     //   List<CryptoWaarde> cryptoWaardeList = haalMeestRecenteCryptoWaardes();

/*        for(CryptoWaarde cryptoWaarde: cryptoWaardeList){
            List<CryptoWaarde> cryptoWaardeHistorischeList = genereerHistorischeCryptoWaardesVanEenCryptomunt
                    (cryptoWaarde, 100, 5);
            for(CryptoWaarde cryptoWaardeHistorisch: cryptoWaardeHistorischeList){
                cryptoWaardeService.slaCryptoWaardeOp(cryptoWaardeHistorisch);
            }
        }*/
        List<Transactie> transactieList = genereerRandomTransacties(3);
        transactieList.forEach(System.out::println);
    }
    public List<Transactie> genereerRandomTransacties(int hoeveelTransacties){
        List<Transactie> transactieList = new ArrayList<>();
        Gebruiker bank = Bank.getInstance();
        List<Cryptomunt> cryptomuntList = assetService.geefAlleCryptomunten();

        for (int i = 0; i < hoeveelTransacties; i++) {
            Transactie transactie = new Transactie();
            Cryptomunt cryptomunt = cryptomuntList.get((genereerRandomGetal(0, 19, 1)));
            transactie.setCryptomunt(cryptomunt);
            LocalDate cryptoDatum = LocalDate.of(2021, 11, genereerRandomGetal(1, 30, 1));
            double prijs = cryptoWaardeService.vindCryptoWaardeOpDatum(cryptomunt, cryptoDatum).getWaarde();
            int koperId = genereerRandomGetal(0, 3000, 1);
            Gebruiker koper;
            if(koperId == 0){
                koper = bank;
            }
            else koper = klantService.vindKlantById(koperId);
            transactie.setKoper(koper);
            int verkoperId = genereerRandomGetal(0, 3000, 1);
            while (koperId == verkoperId) {
                verkoperId = genereerRandomGetal(0, 3000, 1);
            }
            Gebruiker verkoper;
            if(verkoperId == 0){
                verkoper = bank;
            }
            else verkoper = klantService.vindKlantById(verkoperId);
            transactie.setVerkoper(verkoper);
            System.out.println("**** dit is nu de transactie:" + transactie);
            double afwijking = 0;
            if (transactie.getKoper().getGebruikerId() != 0 && transactie.getVerkoper().getGebruikerId() != 0) {
                afwijking = genereerRandomGetal(-500000, 500000, 1) / 10000000.0; // max 5% afwijking
                System.out.println(afwijking);
            }
            transactie.setPrijs(prijs * (1 + afwijking));
            transactie.setAantal(genereerRandomGetal(0, 2000, 1) / 1000.0);
            transactie.setMomentTransactie(LocalDateTime.of(cryptoDatum, LocalTime.NOON));
            transactie.setBankFee(Bank.getInstance().getFee());
            System.out.println(transactie);
            transactieList.add(transactie);
        }
        return transactieList;
    }
    public List<CryptoWaarde> genereerHistorischeCryptoWaardesVanEenCryptomunt(CryptoWaarde cryptoWaarde, int hoeveelWaarden, int afwijkingsPercentage){
        List<CryptoWaarde> cryptoWaardeList = new ArrayList<>();
        double afwijking = 0;
        double waarde = cryptoWaarde.getWaarde();
        for (int i = 0; i < hoeveelWaarden; i++) {
            afwijking = genereerRandomGetal(-100000 + afwijkingsPercentage, 100000 * afwijkingsPercentage, 1) / 10000000.0; // max 1% afwijking
            CryptoWaarde oudereCryptoWaarde = new CryptoWaarde();
            oudereCryptoWaarde.setCryptomunt(cryptoWaarde.getCryptomunt());
            oudereCryptoWaarde.setWaarde(waarde * (1 + afwijking));
            oudereCryptoWaarde.setCryptoWaardeId("default");
            oudereCryptoWaarde.setDatum(cryptoWaarde.getDatum().minusDays(i + 2)); // 1 dag eerder per loop, beginnen op 1e ontbrekende dag
            cryptoWaardeList.add(oudereCryptoWaarde);
        }

        return cryptoWaardeList;
    }

    public List<CryptoWaarde> haalMeestRecenteCryptoWaardes(){
        List<Cryptomunt>  cryptomuntList = CryptoWaardeService.cryptoLijst();
        List<CryptoWaarde> cryptoWaardeList = new ArrayList<>();
        for(Cryptomunt cryptomunt: cryptomuntList){
            cryptoWaardeList.add(cryptoWaardeService.vindMeestRecenteCryptoWaarde(cryptomunt));
        }
        return cryptoWaardeList;
    }
}
