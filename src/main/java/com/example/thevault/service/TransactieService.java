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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class TransactieService {

    private final RootRepository rootRepository;
    private final  KlantService klantService;
    private final  RekeningService rekeningService;
    private final  AssetService assetService;
    private final double TRANSACTION_FEE = Bank.getInstance().getFee();
    private final double DEEL_PRIJSVERSCHIL_KOPER = 0.5;
    private final double DEEL_PRIJSVERSCHIL_VERKOPER = 1 - DEEL_PRIJSVERSCHIL_KOPER;
    private final double DEEL_TRANSACTION_FEE_KOPER = 0.5;
    private final double DEEL_TRANSACTION_FEE_VERKOPER = 1 - DEEL_TRANSACTION_FEE_KOPER;

    @JsonIgnore
    private final Logger logger = LoggerFactory.getLogger(TransactieService.class);

    @Autowired
    public TransactieService(RootRepository rootRepository, KlantService klantService, RekeningService rekeningService,
                             AssetService assetService) {
        super();
        this.rootRepository = rootRepository;
        this.klantService = klantService;
        this.rekeningService = rekeningService;

        this.assetService = assetService;
        logger.info("Nieuwe TransactieService.");
    }

    /**
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

    //TODO check voor negatief aantal toevoegen
    //TODO triggers gebruiken als input? Dan zouden ook de bijbehorende triggers verwijderd moeten worden uit de database
    //TODO check dat triggeraantallen gelijk zijn en bod groter of gelijk is aan vraagprijs
    //TODO prijsformule hier of elders?
    public Transactie sluitTransactie(Gebruiker verkoper, Cryptomunt cryptomunt,
                                      double vraagPrijs, double bod, double aantal, Gebruiker koper, LocalDateTime datumEnTijd) {
        boolean bankIsKoper = (koper instanceof Bank);
        boolean bankIsVerkoper = (verkoper instanceof Bank);
        double prijs, transactieBedragKoper, transactieBedragVerkoper;

/*        if (bankIsKoper || bankIsVerkoper) {
            prijs = berekenPrijsTransactieMetBank(cryptomunt, datumEnTijd);
            transactieBedragKoper = getBedragKoperMetBankAlsVerkoper(aantal, bankIsKoper, prijs);
            transactieBedragVerkoper = getBedragVerkoperMetBankAlsKoper(aantal, bankIsVerkoper, prijs);
        }
        else {
            prijs = vraagPrijs * DEEL_PRIJSVERSCHIL_VERKOPER + bod * DEEL_PRIJSVERSCHIL_KOPER;
            transactieBedragKoper = getBedragKoperBijKlantTransactie(aantal, prijs);
            transactieBedragVerkoper = getBedragVerkoperBijKlantTransactie(aantal, prijs);
        }*/
        prijs = (bankIsKoper || bankIsVerkoper) ? berekenPrijsTransactieMetBank(cryptomunt, datumEnTijd):
                vraagPrijs * DEEL_PRIJSVERSCHIL_VERKOPER + bod * DEEL_PRIJSVERSCHIL_KOPER;
        transactieBedragKoper = (bankIsKoper || bankIsVerkoper) ? getBedragKoperMetBankAlsVerkoper(aantal, bankIsKoper, prijs):
                getBedragKoperBijKlantTransactie(aantal, prijs);
        transactieBedragVerkoper = (bankIsKoper || bankIsVerkoper) ? getBedragVerkoperMetBankAlsKoper(aantal, bankIsVerkoper, prijs):
                getBedragVerkoperBijKlantTransactie(aantal, prijs);

        checkTransactionExceptions(verkoper, cryptomunt, aantal, koper, transactieBedragKoper);
        Transactie transactie = new Transactie(datumEnTijd, verkoper, cryptomunt, prijs, aantal, koper);
        slaAlleAspectenVanTransactieOp(transactie, transactieBedragKoper, transactieBedragVerkoper);

        return transactie;
    }

    private double getBedragVerkoperBijKlantTransactie(double aantal, double prijs) {
        double transactieBedragVerkoper;
        transactieBedragVerkoper = aantal * prijs - TRANSACTION_FEE * DEEL_TRANSACTION_FEE_VERKOPER;
        return transactieBedragVerkoper;
    }

    private double getBedragKoperBijKlantTransactie(double aantal, double prijs) {
        double transactieBedragKoper;
        transactieBedragKoper = aantal * prijs + TRANSACTION_FEE * DEEL_TRANSACTION_FEE_KOPER;
        return transactieBedragKoper;
    }

    private double getBedragVerkoperMetBankAlsKoper(double aantal, boolean bankIsVerkoper, double prijs) {
        double transactieBedragVerkoper;
        transactieBedragVerkoper = (bankIsVerkoper) ? aantal * prijs: aantal * prijs - TRANSACTION_FEE;
        return transactieBedragVerkoper;
    }

    private double getBedragKoperMetBankAlsVerkoper(double aantal, boolean bankIsKoper, double prijs) {
        double transactieBedragKoper;
        transactieBedragKoper = (bankIsKoper) ? aantal * prijs: aantal * prijs + TRANSACTION_FEE;
        return transactieBedragKoper;
    }

    private void checkTransactionExceptions(Gebruiker verkoper, Cryptomunt cryptomunt, double aantal, Gebruiker koper, double transactieBedragKoper) {
        saldoTooLowExceptionHandler(koper, transactieBedragKoper);
        notEnoughCryptoExceptionHandler(verkoper, cryptomunt, aantal);
    }

    // TODO kijken of @Transactional werkt

    /**
     * Deze methode zorgt ervoor dat de transactie opgeslagen wordt en alle relevante rekeningen en
     * portefeuilles worden bijgewerkt.
     * Door de annotatie @Transactional zorgt Spring Boot ervoor dat alle aspecten van deze transactie
     * moeten slagen, anders wordt niets uitgevoerd (Atomisch)
     * @param transactie
     * @param bedragKoper
     * @param bedragVerkoper
     */
    @Transactional
    public void slaAlleAspectenVanTransactieOp(Transactie transactie,
                                        double bedragKoper, double bedragVerkoper) {
        slaTransactieOp(transactie);
        transactie.getKoper().setRekening(rekeningService.wijzigSaldo(transactie.getKoper(), -bedragKoper));
        transactie.getVerkoper().setRekening(rekeningService.wijzigSaldo(transactie.getVerkoper(), bedragVerkoper));
        rekeningService.wijzigSaldo(Bank.getInstance(), transactie.getBankFee());
        assetService.wijzigAssetGebruiker(transactie.getKoper(), transactie.getCryptomunt(), -transactie.getAantal());
        assetService.wijzigAssetGebruiker(transactie.getVerkoper(), transactie.getCryptomunt(), transactie.getAantal());

    }


    /**
     * Berekent de prijs wanneer direct met de bank gehandeld wordt door het aantal te verkopen of te kopen
     * cryptomunten met de huidige koerswaarde van de cryptomunt te vermenigvuldigen
     *
     * @param cryptomunt waarmee gehandeld zal worden
     * @return de prijs
    * */
    public double berekenPrijsTransactieMetBank(Cryptomunt cryptomunt, LocalDateTime datumEnTijd){
        return rootRepository.haalCryptoWaardeOpDatum(cryptomunt, datumEnTijd.toLocalDate()).getWaarde();
    }


    /**
     * Methode slaat transactie op
    *
     * @param transactie
     * @retrun transactie die zojuist is opgeslagen
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
     * @throws NotEnoughCryptoException exception die een NOT_ACCEPTABLE http status teruggeeft
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
     * @throws BalanceTooLowException exception die een NOT_ACCEPTABLE hhtp status teruggeeft
    * */
    public void saldoTooLowExceptionHandler(Gebruiker koper, double bedrag) throws BalanceTooLowException  {
        double saldoGebruiker = rootRepository.vindRekeningVanGebruiker(koper).getSaldo();
        System.out.println("**** Het saldo van de koper is: " + saldoGebruiker);
        System.out.println("**** Het bedrag is: " + bedrag);
        if((saldoGebruiker - bedrag) < 0 ) {
            logger.info("Saldo koper te laag voor deze transactie.");
            throw new BalanceTooLowException();
        }
    }
}


