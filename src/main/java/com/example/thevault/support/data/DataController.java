// Created by S.C. van Gils
// Creation date 20-12-2021

/**
 * Deze klasse is bedoeld om de database te kunnen vullen met random gegenereerde data
 */

package com.example.thevault.support.data;

import com.example.thevault.domain.model.*;
import com.example.thevault.service.*;
import com.example.thevault.support.authorization.AuthorizationService;
import com.example.thevault.support.exceptions.BalanceTooLowException;
import com.example.thevault.support.exceptions.NotEnoughCryptoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.example.thevault.support.data.DataGenerator.genereerRandomGetal;

@Component
 public class DataController implements ApplicationListener<ContextRefreshedEvent> {

    public static final int BITCOIN_ID = 1;
    public static final int ETHEREUM_ID = 1027;
    public static final String BESTANDSNAAM_RANDOM_DATASET = "Sprint2/datacsv.csv"; // gebruikt om huidige database te vullen
    public static final int UITGANGSJAAR = 2021;
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



    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // gebruik hier onderstaande functies om data te genereren
    //   cryptoWaardeService.haalCryptoWaardes();

      Gebruiker  testKlant1 = new Klant("Carmen", "GoedWachtwoord",
              null, null, null,"Carmen", null, 123456789, LocalDate.parse("1985-12-30"));
        Gebruiker      testKlant2 = new Klant("Jolien", "BeterWachtwoord",
                null, null, null, "Jolien",null, 987654321, LocalDate.parse("1985-10-14"));
        testKlant1.setGebruikerId(1);
        testKlant2.setGebruikerId(2);

      Cryptomunt  testCryptomunt1 = new Cryptomunt(1, "BITCOIN", "BCN" );
      Cryptomunt  testCryptomunt2 = new Cryptomunt(2, "ETHERIUM", "ETH");
        Trigger triggerKoper = new TriggerKoper(testKlant1, testCryptomunt1, 1000.0, 2.0);
        Trigger triggerVerkoper = new TriggerVerkoper(testKlant2, testCryptomunt1, 950.0, 2.0);
        Transactie transactie = new Transactie(LocalDateTime.now(), triggerKoper, triggerVerkoper);
        System.out.println(transactie.getPrijs());
    }


    public void vulKlantAdresEnRekeningTabel(int hoeveelKlanten, String bestandsnaam) throws IOException {
        List<Klant> list = DataGenerator.maakLijstKlantenVanCSV(bestandsnaam, hoeveelKlanten);
        for (Klant klant : list) {
            registrationService.registreerKlant(klant);
        }
    }
// TODO trigger incorporeren

/*    public void slaRandomTransactiesOp(RandomDataInput randomDataInput){
        List<Transactie> transacties = genereerRandomTransacties(randomDataInput);
        transacties.sort(new TransactieComparator());
        for(Transactie transactie: transacties){
            try {
                transactieService.sluitTransactie(transactie.getVerkoper(), transactie.getCryptomunt(),
                        transactie.getPrijs(), transactie.getPrijs(), transactie.getAantal(), transactie.getKoper(),
                        transactie.getMomentTransactie());
            }
            catch (NotEnoughCryptoException | BalanceTooLowException notEnoughCryptoException){
                System.out.println(transactie);
            }
        }
    }*/

    public List<Transactie> genereerRandomTransacties(RandomDataInput randomDataInput){
        List<Cryptomunt> cryptomuntList = assetService.geefAlleCryptomunten();
        return creeerTransactieLijst(randomDataInput, cryptomuntList);
    }

    private List<Transactie> creeerTransactieLijst(RandomDataInput randomDataInput, List<Cryptomunt> cryptomuntList) {
        List<Transactie> transactieList = new ArrayList<>();
        for (int i = 0; i < randomDataInput.getHoeveelTransacties(); i++) {
            Transactie transactie = getRandomTransactie(randomDataInput, cryptomuntList);
            transactieList.add(transactie);
        }
        return transactieList;
    }

    private Transactie getRandomTransactie(RandomDataInput randomDataInput, List<Cryptomunt> cryptomuntList) {
        Cryptomunt cryptomunt = cryptomuntList.get((genereerRandomGetal(0, cryptomuntList.size(), 1)));
        LocalDate cryptoDatum = LocalDate.of(UITGANGSJAAR, randomDataInput.getTransactieDataRange().getMaand(),
                genereerRandomGetal(1, 30, 1));
        double prijs = cryptoWaardeService.vindCryptoWaardeOpDatum(cryptomunt, cryptoDatum).getWaarde();
        int koperId = getRandomGebruikerId(randomDataInput.getTransactieDataRange().getAantalGebruikers(),
                randomDataInput.getBankAlsTransactiePartij().isBankAlsKoper());
        int verkoperId = getRandomGebruikerId(randomDataInput.getTransactieDataRange().getAantalGebruikers(),
                randomDataInput.getBankAlsTransactiePartij().isBankAlsVerkoper());
        verkoperId = getVerkoperId(koperId, verkoperId);
        double afwijking = getAfwijkingPrijs(koperId, verkoperId, randomDataInput.getRandomTransactieRange().getMaxAfwijkingPrijs());
        double randomAantal = getRandomAantal(cryptomunt, randomDataInput.getRandomTransactieRange().getMaxAantal());
        Gebruiker koper = getTransactiepartij(koperId);
        Trigger triggerKoper = new TriggerKoper(koper, cryptomunt, afwijking, randomAantal);
        Gebruiker verkoper = getTransactiepartij(verkoperId);
        LocalDateTime randomDatumTijd = LocalDateTime.of(cryptoDatum, genereerRandomTijdstip());
        return setTransactie(prijs, koper, verkoper, cryptomunt, afwijking, randomAantal, randomDatumTijd);
    }

    private int getRandomGebruikerId(int aantalGebruikers, boolean bankAlsKoperOfVerkoper) {
        int gebruikerId = genereerRandomGetal(0, aantalGebruikers, 1);
        if (bankAlsKoperOfVerkoper) {
            gebruikerId = 0;
        }
        return gebruikerId;
    }

    private Transactie setTransactie(double prijs, Gebruiker koper, Gebruiker verkoper, Cryptomunt cryptomunt,
                                     double afwijking, double randomAantal, LocalDateTime randomDatumTijd) {
        Transactie transactie = new Transactie();
        transactie.setKoper(koper);
        transactie.setVerkoper(verkoper);
        transactie.setCryptomunt(cryptomunt);
        transactie.setPrijs(prijs * (1 + afwijking));
        transactie.setAantal(randomAantal);
        transactie.setMomentTransactie(randomDatumTijd);
        transactie.setBankFee(Bank.getInstance().getFee());
        return transactie;
    }

    private double getAfwijkingPrijs(int koperId, int verkoperId, int maxAfwijkingsPercentage) {
        double afwijking = 0;
        if (koperId != 0 && verkoperId != 0) {
            afwijking = genereerRandomGetal(-100000 * maxAfwijkingsPercentage,
                    100000 * maxAfwijkingsPercentage, 1) / 10000000.0;

        }
        return afwijking;
    }

    private double getRandomAantal(Cryptomunt cryptomunt, double maxAantal) {
        double randomAantal = genereerRandomGetal(0, (int) maxAantal * 1000, 1) / 1000.0;
        if(cryptomunt.getId() == BITCOIN_ID || cryptomunt.getId() == ETHEREUM_ID){ // bedrag wordt snel te hoog met deze munten
            randomAantal = genereerRandomGetal(0, 10, 1) / 1000.0;
        }
        return randomAantal;
    }

    private int getVerkoperId(int koperId, int verkoperId) {
        while (koperId == verkoperId) {
            verkoperId = genereerRandomGetal(1, 60, 1);
        }
        return verkoperId;
    }

    private Gebruiker getTransactiepartij(int gebruikerId) {
        Gebruiker verkoper;
        if (gebruikerId == 0) {
            verkoper = Bank.getInstance();
        } else verkoper = klantService.vindKlantById(gebruikerId);
        return verkoper;
    }

    private LocalTime genereerRandomTijdstip() {
        int randomUur = genereerRandomGetal(0, 23, 1);
        int randomMinuut = genereerRandomGetal(0, 59, 1);
        int randomSeconde = genereerRandomGetal(0,59,1);
        return LocalTime.of(randomUur, randomMinuut, randomSeconde);
    }

    public void slaHistorischeCryptoWaardenOp(int hoeveelWaarden, int afwijkingsPercentage){
        List<CryptoWaarde> cryptoWaardeList = cryptoWaardeService.haalMeestRecenteCryptoWaardes();
        for(CryptoWaarde cryptoWaarde: cryptoWaardeList){
            List<CryptoWaarde> cryptoWaardeHistorischeList = genereerHistorischeCryptoWaardesVanEenCryptomunt
                    (cryptoWaarde, hoeveelWaarden, afwijkingsPercentage);
            for(CryptoWaarde cryptoWaardeHistorisch: cryptoWaardeHistorischeList){
                cryptoWaardeService.slaCryptoWaardeOp(cryptoWaardeHistorisch);
            }
        }
    }


    /**
     *
     * @param cryptoWaarde de echte cryptowaarde die als startpunt wordt gebruikt
     * @param hoeveelWaarden er wordt per dag een cryptowaarde gegenereerd, dit bepaalt hoeveel dagwaarden je genereert
     * @param afwijkingsPercentage hoeveel schommelt de koers per dag maximaal
     * @return een lijst van gegenereerde cryptowaardes
     */
    public List<CryptoWaarde> genereerHistorischeCryptoWaardesVanEenCryptomunt(CryptoWaarde cryptoWaarde, int hoeveelWaarden, int afwijkingsPercentage){
        List<CryptoWaarde> cryptoWaardeList = new ArrayList<>();
        double afwijking = 0;
        double waarde = cryptoWaarde.getWaarde();
        for (int i = 0; i < hoeveelWaarden; i++) {
            afwijking = genereerRandomGetal(-100000 + afwijkingsPercentage, 100000 * afwijkingsPercentage, 1) / 10000000.0;
            CryptoWaarde oudereCryptoWaarde = new CryptoWaarde();
            oudereCryptoWaarde.setCryptomunt(cryptoWaarde.getCryptomunt());
            oudereCryptoWaarde.setWaarde(waarde * (1 + afwijking));
            oudereCryptoWaarde.setCryptoWaardeId("default");
            oudereCryptoWaarde.setDatum(cryptoWaarde.getDatum().minusDays(i + 1));
            cryptoWaardeList.add(oudereCryptoWaarde);
        }

        return cryptoWaardeList;
    }

    public static class TransactieComparator implements Comparator<Transactie>{

        @Override
        public int compare(Transactie o1, Transactie o2) {
            return o1.getMomentTransactie().compareTo(o2.getMomentTransactie());
        }
    }
    public void creeerPortefeuilleVoorBank(){
        Gebruiker bank = Bank.getInstance();
        List<Cryptomunt> cryptomuntList = assetService.geefAlleCryptomunten();
        for(Cryptomunt cryptomunt: cryptomuntList){
            Asset asset = new Asset(cryptomunt, 100000);
            asset.setGebruiker(bank);
            assetService.slaNieuwAssetOp(asset);
        }
        assetService.vulPortefeuilleVanGebruiker(bank);
    }
/*
    public void integratieTestSluitTransactie(){
        Gebruiker klant = loginService.vindKlantByGebruikersnaam("LavernRoman");
        Gebruiker andereKlant = loginService.vindKlantByGebruikersnaam("ColumbusMccoy");
        Gebruiker bank = Bank.getInstance();
        List<Cryptomunt> cryptomuntList = assetService.geefAlleCryptomunten();

           cryptoWaardeService.haalCryptoWaardes();
        Cryptomunt bitcoin = cryptomuntList.get(0);
        CryptoWaarde bitcoinWaarde = cryptoWaardeService.vindMeestRecenteCryptoWaarde(bitcoin);
        transactieService.sluitTransactie(bank, bitcoin, bitcoinWaarde.getWaarde(), bitcoinWaarde.getWaarde(), 0.001, klant, LocalDateTime.now());
        // trigger met bank als tegenpartij
        transactieService.sluitTransactie(bank, bitcoin, bitcoinWaarde.getWaarde(), bitcoinWaarde.getWaarde() * (1 + 0.02), 0.001, klant, LocalDateTime.now());
        // trigger met andere klant als tegenpartij
        transactieService.sluitTransactie(klant, bitcoin, bitcoinWaarde.getWaarde() * (1 - 0.02) , bitcoinWaarde.getWaarde() * (1 + 0.01), 0.001, andereKlant, LocalDateTime.now());

    }*/
}
