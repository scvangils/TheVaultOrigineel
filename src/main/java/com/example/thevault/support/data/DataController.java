// Created by S.C. van Gils
// Creation date 20-12-2021

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

    }
    public void vulKlantAdresEnRekeningTabel(int hoeveelKlanten) throws IOException {
        List<Klant> list = DataGenerator.maakLijstKlantenVanCSV("Sprint2/datacsv.csv", hoeveelKlanten);
        for (Klant klant : list) {
            registrationService.registreerKlant(klant);
        }
    }

    public void slaRandomTransactiesOp(int hoeveelTransacties, int aantalGebruikers,
                                       boolean bankAlsKoper, boolean bankAlsVerkoper, int maand, double maxAantal){
        List<Transactie> transactiesMetBankAlsVerkoper = genereerRandomTransacties(hoeveelTransacties, aantalGebruikers,
                bankAlsKoper, bankAlsVerkoper, maand, maxAantal);
        transactiesMetBankAlsVerkoper.sort(new TransactieComparator());
        for(Transactie transactie: transactiesMetBankAlsVerkoper){
            try {
                transactieService.sluitTransactie(transactie.getVerkoper(), transactie.getCryptomunt(),
                        transactie.getPrijs(), transactie.getPrijs(), transactie.getAantal(), transactie.getKoper(),
                        transactie.getMomentTransactie());
            }
            catch (NotEnoughCryptoException | BalanceTooLowException notEnoughCryptoException){
                System.out.println(transactie);
            }
        }
    }

    public List<Transactie> genereerRandomTransacties(int hoeveelTransacties, int aantalGebruikers, boolean bankAlsKoper,
                                                      boolean bankAlsVerkoper, int maand, double maxAantal){
        List<Transactie> transactieList = new ArrayList<>();
        Gebruiker bank = Bank.getInstance();
        List<Cryptomunt> cryptomuntList = assetService.geefAlleCryptomunten();

        creeerTransactieLijst(hoeveelTransacties, aantalGebruikers, bankAlsKoper, bankAlsVerkoper, maand, transactieList, bank, cryptomuntList, maxAantal);
        return transactieList;
    }

    private void creeerTransactieLijst(int hoeveelTransacties, int aantalGebruikers, boolean bankAlsKoper, boolean bankAlsVerkoper,
                                       int maand, List<Transactie> transactieList, Gebruiker bank, List<Cryptomunt> cryptomuntList, double maxAantal) {
        for (int i = 0; i < hoeveelTransacties; i++) {

            Cryptomunt cryptomunt = cryptomuntList.get((genereerRandomGetal(0, cryptomuntList.size(), 1)));
            LocalDate cryptoDatum = LocalDate.of(2021, maand, genereerRandomGetal(1, 30, 1));
            double prijs = cryptoWaardeService.vindCryptoWaardeOpDatum(cryptomunt, cryptoDatum).getWaarde();
            int koperId = getRandomGebruikerId(aantalGebruikers, bankAlsKoper);
            int verkoperId = getRandomGebruikerId(aantalGebruikers, bankAlsVerkoper);
            verkoperId = getVerkoperId(koperId, verkoperId);
            double afwijking = getAfwijking(koperId, verkoperId);
            double randomAantal = getRandomAantal(cryptomunt, maxAantal);
            Gebruiker koper = getTransactiepartij(bank, koperId);
            Gebruiker verkoper = getTransactiepartij(bank, verkoperId);
            LocalDateTime randomDatumTijd = LocalDateTime.of(cryptoDatum, genereerRandomTijdstip());
            Transactie transactie = setTransactie(prijs, koper, verkoper, cryptomunt, afwijking, randomAantal, randomDatumTijd);
            transactieList.add(transactie);
        }
    }

    private int getRandomGebruikerId(int aantalGebruikers, boolean bankAlsVerkoper) {
        int verkoperId = genereerRandomGetal(0, aantalGebruikers, 1);
        if (bankAlsVerkoper) {
            verkoperId = 0;
        }
        return verkoperId;
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

    private double getAfwijking(int koperId, int verkoperId) {
        double afwijking = 0;
        if (koperId != 0 && verkoperId != 0) {
            afwijking = genereerRandomGetal(-500000, 500000, 1) / 10000000.0; // max 5% afwijking

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

    private Gebruiker getTransactiepartij(Gebruiker bank, int gebruikerId) {
        Gebruiker verkoper;
        if (gebruikerId == 0) {
            verkoper = bank;
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
        bank.getPortefeuille().forEach(System.out::println);
    }
    public void integratieTestSluitTransactie(){
        Gebruiker klant = loginService.vindKlantByGebruikersnaam("LavernRoman");
        Gebruiker andereKlant = loginService.vindKlantByGebruikersnaam("ColumbusMccoy");
        Gebruiker bank = Bank.getInstance();
        List<Cryptomunt> cryptomuntList = assetService.geefAlleCryptomunten();

           cryptoWaardeService.haalCryptoWaardes();
        Cryptomunt bitcoin = cryptomuntList.get(0);
        CryptoWaarde bitcoinWaarde = cryptoWaardeService.vindMeestRecenteCryptoWaarde(bitcoin);
        transactieService.sluitTransactie(bank, bitcoin, bitcoinWaarde.getWaarde(), bitcoinWaarde.getWaarde(), 0.001, klant, LocalDateTime.now());
        transactieService.sluitTransactie(bank, bitcoin, bitcoinWaarde.getWaarde(), bitcoinWaarde.getWaarde() + (1 + 0.02), 0.001, klant, LocalDateTime.now());

    }
}
