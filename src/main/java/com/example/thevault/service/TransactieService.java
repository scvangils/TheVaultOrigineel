// Created by E.S. Olthof
// Creation date 15-12-2020

package com.example.thevault.service;

import com.example.thevault.domain.mapping.repository.RootRepository;
import com.example.thevault.domain.model.*;
import com.example.thevault.support.exceptions.BalanceTooLowException;
import com.example.thevault.support.exceptions.NotEnoughCryptoException;
import com.example.thevault.domain.model.Asset;
import net.minidev.json.annotate.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.OffsetDateTime;

@Service
public class TransactieService {

    private final RootRepository rootRepository;
    private final  KlantService klantService;
    private final  RekeningService rekeningService;
    private final  CryptoWaardeService cryptoWaardeService;
    private final  AssetService assetService;
    private final double TRANSACTION_FEE = 1.50;

    @JsonIgnore
    private final Logger logger = LoggerFactory.getLogger(TransactieService.class);

    @Autowired
    public TransactieService(RootRepository rootRepository, KlantService klantService, RekeningService rekeningService, CryptoWaardeService cryptoWaardeService, AssetService assetService) {
        super();
        this.rootRepository = rootRepository;
        this.klantService = klantService;
        this.rekeningService = rekeningService;
        this.cryptoWaardeService = cryptoWaardeService;
        this.assetService = assetService;
        logger.info("Nieuwe TransactieService.");
    }

    /**
     * TODO afronden!!!
     * Maak een transactie op basis van een bedrag, cryptomunt, koper en verkoper en een datum.
     * Als de koper niet genoeg saldo heeft of wanneer de verkoper niet het aantal cryptomunten
     * heeft dat deze wilt verkopen geeft de methode een foutmelding
     *
     * @param verkoper verkoper van cryptomunt
     * @param cryptomunt het type cryptomunt
     * @param prijs het bedrag dat voor de cryptomunt betaald moet worden
     * @param aantal de hoeveelheid cryptomunt
     * @param koper de koper van de cryptomunt in deze transactie
     *
     * @return transactie
    * */
    public Transactie sluitTransactie(Gebruiker verkoper, Cryptomunt cryptomunt,
                                      double prijs, double aantal, Gebruiker koper) {

        saldoTooLowExceptionHandler(koper, prijs);
        notEnoughCryptoExceptionHandler(verkoper, cryptomunt, aantal);


        Rekening rekeningKoper = rootRepository.vindRekeningVanGebuiker(koper);
        Rekening rekeningVerkoper = rootRepository.vindRekeningVanGebuiker( verkoper);
        logger.info("REKENING KOPER VOOR TRANSACTIE: " + rekeningKoper);
        logger.info("REKENING VERKOPER VOOR TRANSACTIE: " + rekeningVerkoper);

        Asset assetKoper = rootRepository.geefAssetVanGebruiker(koper, cryptomunt);
        Asset assetVerkoper = rootRepository.geefAssetVanGebruiker(verkoper, cryptomunt);
        logger.info("ASSET KOPER VOOR TRANSACTIE: " + assetKoper);
        logger.info("ASSET VERKOPER VOOR TRANSACTIE: " + assetVerkoper);

        if (rekeningVerkoper.getGebruiker() instanceof Bank || rekeningKoper.getGebruiker() instanceof Bank) {
            prijs =+ TRANSACTION_FEE;
        }

        rekeningService.wijzigSaldo(rekeningKoper, rekeningKoper.getSaldo() - prijs);
        rekeningService.wijzigSaldo(rekeningVerkoper, rekeningVerkoper.getSaldo() + prijs);
        assetKoper.setAantal(assetKoper.getAantal() + aantal);
        assetKoper.setGebruiker(koper);
        assetVerkoper.setAantal(assetVerkoper.getAantal() - aantal);
        assetVerkoper.setGebruiker(verkoper);
        assetService.wijzigAssetGebruiker(assetKoper);
        assetService.wijzigAssetGebruiker(assetVerkoper);

        // maak nieuwe transactie aan
        Transactie transactie = new Transactie(OffsetDateTime.now(), verkoper, cryptomunt, prijs, aantal, koper);
        logger.info("ASSET KOPER NA TRANSACTIE: " + assetKoper);
        logger.info("ASSET VERKOPER NA TRANSACTIE: " + assetVerkoper);
        logger.info("REKENING KOPER NA TRANSACTIE: " + rekeningKoper);
        logger.info("REKENING VERKOPER NA TRANSACTIE: " + rekeningVerkoper);

        slaTransactieOp(transactie);
        return transactie;
    }

    public Transactie sluitTransactie1(Gebruiker verkoper, Cryptomunt cryptomunt,
                                      double vraagPrijs, double bod, double aantal, Gebruiker koper) {
        // bepaal prijs transactie
        double prijs;
        if (koper instanceof Bank || verkoper instanceof Bank) {
            prijs = berekenPrijsTransactieMetBank(cryptomunt, aantal);
        } else {
            prijs = vraagPrijs + bod / 2.0;
        }

        // exceptions:
        saldoTooLowExceptionHandler(koper, prijs);
        notEnoughCryptoExceptionHandler(verkoper, cryptomunt, aantal);

        //haal informatie op uit database (asset + rekening)
        Rekening rekeningKoper = rootRepository.vindRekeningVanGebuiker(koper);
        Rekening rekeningVerkoper = rootRepository.vindRekeningVanGebuiker( verkoper);
        Asset assetKoper = rootRepository.geefAssetVanGebruiker(koper, cryptomunt);
        Asset assetVerkoper = rootRepository.geefAssetVanGebruiker(verkoper, cryptomunt);

        // wijzig de saldo's en assets van de verkoper en koper:
        rekeningService.wijzigSaldo(rekeningKoper, rekeningKoper.getSaldo() - prijs);
        rekeningService.wijzigSaldo(rekeningVerkoper, rekeningVerkoper.getSaldo() + prijs);
        assetKoper.setAantal(assetKoper.getAantal() + aantal);
        assetKoper.setGebruiker(koper);
        assetVerkoper.setAantal(assetVerkoper.getAantal() - aantal);
        assetVerkoper.setGebruiker(verkoper);
        assetService.wijzigAssetGebruiker(assetKoper);
        assetService.wijzigAssetGebruiker(assetVerkoper);

        // maak nieuwe transactie aan
        Transactie transactie = new Transactie(OffsetDateTime.now(), verkoper, cryptomunt, prijs, aantal, koper);

        slaTransactieOp(transactie);
        return transactie;
    }


    public double berekenPrijsTransactieMetBank (Cryptomunt cryptomunt, double aantal){
        return aantal * cryptoWaardeService.vindCryptoWaarde(cryptomunt).getWaarde() + TRANSACTION_FEE;
    }

    public Transactie sluitTransactieMetKlant(){
        return null;
    }


    /**
     * Methode slaat transactie op
    *
     * @param transactie
     * @retrun transactie die zojuist is opgeslagen
     *
    * */
    public Transactie slaTransactieOp(Transactie transactie){
        return rootRepository.slaTransactieOp(transactie);
    }

    /***
     * Checkt of de verkoper genoeg cryptomunten heeft om de transactie
     * te kunnen sluiten
     *
     * @param verkoper klant-object verkoper
     * @param cryptomunt Cryptomunt-object
     * @param aantal double hoeveelheid cryptomunt
     *
     * */
    public void notEnoughCryptoExceptionHandler (Gebruiker verkoper, Cryptomunt cryptomunt, double aantal) throws NotEnoughCryptoException {
        Asset assetVerkoper = klantService.geefAssetMetCryptoMuntVanGebruiker(verkoper, cryptomunt);
        if(assetVerkoper == null || assetVerkoper.getAantal() - aantal  < 0) {
            logger.info("Te weinig cryptomunten in bezit van verkoper voor deze transactie");
            throw new NotEnoughCryptoException();
        }
    }

    /**
     * Controleert of het saldo van de koper toereikend is voor de
     * transactie
     *
     * @param koper van de cryptomunt
     * @param prijs verkoopbedrag van cryptomunt
     *
    * */
    public void saldoTooLowExceptionHandler(Gebruiker koper, double prijs) throws BalanceTooLowException  {
        double saldoGebruiker = rootRepository.vindRekeningVanGebuiker(koper).getSaldo();
        if((saldoGebruiker - prijs) < 0 ) {
            logger.info("Saldo koper te laag voor deze transactie.");
            throw new BalanceTooLowException();
        }
    }
    public RootRepository getRootRepository() {
        return rootRepository;
    }
}


