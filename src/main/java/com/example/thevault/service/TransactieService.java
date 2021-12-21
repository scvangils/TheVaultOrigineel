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


import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

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
     * @param vraagPrijs het bedrag dat voor de cryptomunt gevraagd wordt
     * @param bod het bod dat op de cryptomunt gedaan wordt
     * @param aantal de hoeveelheid cryptomunt
     * @param koper de koper van de cryptomunt in deze transactie
     *
     * @return transactie
    * */

    public Transactie sluitTransactie(Gebruiker verkoper, Cryptomunt cryptomunt,
                                      double vraagPrijs, double bod, double aantal, Gebruiker koper) {
        // bepaal prijs transactie
        double prijs;
        if (koper instanceof Bank || verkoper instanceof Bank) {
            prijs = berekenPrijsTransactieMetBank(cryptomunt, aantal);
        } else {
            prijs = vraagPrijs + bod / 2.0;
        }

        // exceptions:
        saldoTooLowExceptionHandler(koper, (prijs*aantal)+TRANSACTION_FEE);
        notEnoughCryptoExceptionHandler(verkoper, cryptomunt, aantal);

        //haal informatie op uit database (asset + rekening)
        Rekening rekeningKoper = rootRepository.vindRekeningVanGebuiker(koper);
        Rekening rekeningVerkoper = rootRepository.vindRekeningVanGebuiker( verkoper);
        Asset assetKoper = rootRepository.geefAssetVanGebruiker(koper, cryptomunt);
        Asset assetVerkoper = rootRepository.geefAssetVanGebruiker(verkoper, cryptomunt);

        // wijzig de saldo's en assets van de verkoper en koper:
        rekeningService.wijzigSaldo(koper, (rekeningKoper.getSaldo() - (prijs +(TRANSACTION_FEE/2))));
        rekeningService.wijzigSaldo(koper, (rekeningVerkoper.getSaldo() + (prijs + (TRANSACTION_FEE/2))));

        assetService.wijzigAssetGebruiker(assetKoper);
        assetService.wijzigAssetGebruiker(assetVerkoper);

        // maak nieuwe transactie aan
        Transactie transactie = new Transactie(LocalDateTime.now(), verkoper, cryptomunt, prijs, aantal, koper, TRANSACTION_FEE);

        slaTransactieOp(transactie);
        return transactie;
    }


    /**
     * Berekend de prijs wanneer direct met de bank gehandeld wordt door het aantal te verkopen of te kopen
     * cryptomunten met de huidige koerswaarde van de cryptomunt te vermenigvuldigen en daar
     * een transaction fee bij op te tellen.
     *
     * @param cryptomunt waarmee gehandeld zal worden
     * @param aantal de hoeveelheid van de cryptomunt als double waarde
     * @return de prijs inclusief fee
    * */
    public double berekenPrijsTransactieMetBank (Cryptomunt cryptomunt, double aantal){
        return aantal * cryptoWaardeService.vindCryptoWaarde(cryptomunt).getWaarde() + TRANSACTION_FEE;
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
     * @param bedrag verkoopbedrag van cryptomunt
     *
    * */
    public void saldoTooLowExceptionHandler(Gebruiker koper, double bedrag) throws BalanceTooLowException  {
        double saldoGebruiker = rootRepository.vindRekeningVanGebuiker(koper).getSaldo();
        if((saldoGebruiker - bedrag) < 0 ) {
            logger.info("Saldo koper te laag voor deze transactie.");
            throw new BalanceTooLowException();
        }
    }
    public RootRepository getRootRepository() {
        return rootRepository;
    }
}


