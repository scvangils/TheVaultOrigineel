// Created by E.S. Olthof
// Creation date 15-12-2020

package com.example.thevault.service;

import com.example.thevault.domain.mapping.repository.RootRepository;
import com.example.thevault.domain.model.*;
import com.example.thevault.support.exceptions.BalanceTooLowException;
import com.example.thevault.support.exceptions.NotEnoughCryptoException;
import net.minidev.json.annotate.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
public class TransactieService {

    private RootRepository rootRepository;
    private KlantService klantService;
    private RekeningService rekeningService;

    @JsonIgnore
    private final Logger logger = LoggerFactory.getLogger(RekeningService.class);

    @Autowired
    public TransactieService(RootRepository rootRepository, KlantService klantService, RekeningService rekeningService) {
        super();
        this.rootRepository = rootRepository;
        this.klantService = klantService;
        this.rekeningService = rekeningService;
        logger.info("New TransactieService.");
    }

    /**
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
     * @return boolean (true als de transactie geslaagd is)
    * */
    public Transactie sluitTransactie(Klant verkoper, Cryptomunt cryptomunt,
                                  double prijs, double aantal, Klant koper) {

        // handel eventuele exceptions af
        saldoTooLowExceptionHandler(koper, prijs);
        notEnoughCryptoExceptionHandler(verkoper, cryptomunt, aantal);

        //wijzig saldo van de klanten op basis van de transactie
        rekeningService.wijzigSaldo(koper.getRekening(), (koper.getRekening().getSaldo() - prijs));
        rekeningService.wijzigSaldo(verkoper.getRekening(), verkoper.getRekening().getSaldo() + prijs);

        // maak nieuwe transactie aan
        Transactie transactie = new Transactie(OffsetDateTime.now(), verkoper, cryptomunt, prijs, aantal, koper);
        slaTransactieOp(transactie);
        return transactie;
    }

/*    public Transactie sluitTransactieMetBank(Klant verkoper, Cryptomunt cryptomunt,
                                      double bedrag, double aantal, Klant koper) {

        // handel eventuele exceptions af
        saldoTooLowExceptionHandler(koper, bedrag);
        notEnoughCryptoExceptionHandler(verkoper, cryptomunt, aantal);

        //wijzig saldo van de klanten op basis van de transactie
        rekeningService.wijzigSaldo(koper.getRekening(), (koper.getRekening().getSaldo() - bedrag));
        rekeningService.wijzigSaldo(verkoper.getRekening(), verkoper.getRekening().getSaldo() + bedrag);

        // maak nieuwe transactie aan
        Transactie transactie = new Transactie(OffsetDateTime.now(), verkoper, cryptomunt, bedrag, aantal, koper);
        return slaTransactieOp(transactie);
    }*/

    /**
     * Methode slaat transactie op
    *
     * @param transactie
     * @retrun transactie die zojuist is opgeslagen
     *
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
    public void notEnoughCryptoExceptionHandler (Klant verkoper, Cryptomunt cryptomunt, double aantal) throws NotEnoughCryptoException {
        Asset assetVerkoper = klantService.geefAssetMetCryptoMuntVanKlant(verkoper, cryptomunt);
        if(assetVerkoper == null || assetVerkoper.getAantal() <aantal) {
            logger.info("Te weinig cryptomunten in bezit van verkoper voor deze transactie");
            throw new NotEnoughCryptoException();
        }
    }

    /**
     * TO DO aanpassen naar nieuwe methode wijzigSaldo
     * Controleert of het saldo van de koper toereikend is voor de
     * transactie
     *
     * @param koper van de cryptomunt
     * @param bedrag verkoopbedrag van cryptomunt
     *
    * */
    public void saldoTooLowExceptionHandler(Klant koper, Double bedrag) throws BalanceTooLowException  {
        if((koper.getRekening().getSaldo() - bedrag) < 0) {
            logger.info("Saldo koper te laag voor deze transactie.");
            throw new BalanceTooLowException();
        }
    }
    public RootRepository getRootRepository() {
        return rootRepository;
    }
}


