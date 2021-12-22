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
import java.time.LocalDateTime;

@Service
public class TransactieService {

    private final RootRepository rootRepository;
    private final  KlantService klantService;
    private final  RekeningService rekeningService;
    private final  CryptoWaardeService cryptoWaardeService;
    private final  AssetService assetService;
    private final double TRANSACTION_FEE = 1.50;
    private final double VERDELING_PRIJSVERSCHIL = 2.0;

    @JsonIgnore
    private final Logger logger = LoggerFactory.getLogger(TransactieService.class);

    @Autowired
    public TransactieService(RootRepository rootRepository, KlantService klantService, RekeningService rekeningService,
                             CryptoWaardeService cryptoWaardeService, AssetService assetService) {
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
        assetService.vulPortefeuilleVanGebruiker(verkoper);
        boolean bankIsKoper = (koper instanceof Bank);
        boolean bankIsVerkoper = (verkoper instanceof Bank);
        // bepaal prijs transactie en transactiebedragen
        double prijs;
        double transactieBedragKoper;
        double transactieBedragVerkoper;
        if (bankIsKoper || bankIsVerkoper) {
            prijs = berekenPrijsTransactieMetBank(cryptomunt);
            transactieBedragKoper = (bankIsKoper) ? aantal * prijs: aantal * prijs + TRANSACTION_FEE;
            transactieBedragVerkoper = (bankIsVerkoper) ? aantal * prijs: aantal * prijs - TRANSACTION_FEE;
        }
        else {
            prijs = (vraagPrijs + bod) / VERDELING_PRIJSVERSCHIL;
            transactieBedragKoper = aantal * prijs + TRANSACTION_FEE / 2;
            transactieBedragVerkoper = aantal * prijs - TRANSACTION_FEE / 2;
        }
        // exceptions:
        checkTransactionExceptions(verkoper, cryptomunt, aantal, koper, transactieBedragKoper);
        // maak nieuwe transactie aan
        Transactie transactie = setTransactie(verkoper, cryptomunt, aantal, koper, prijs);
        // wijzig de saldo's en assets van de verkoper en koper:
        setRekeningEnPortefeuilleNaTransactie(verkoper, cryptomunt, koper, transactieBedragKoper, transactieBedragVerkoper, aantal);

        return transactie;
    }

    private void checkTransactionExceptions(Gebruiker verkoper, Cryptomunt cryptomunt, double aantal, Gebruiker koper, double transactieBedragKoper) {
        saldoTooLowExceptionHandler(koper, transactieBedragKoper);
        notEnoughCryptoExceptionHandler(verkoper, cryptomunt, aantal);
    }

    private Transactie setTransactie(Gebruiker verkoper, Cryptomunt cryptomunt, double aantal, Gebruiker koper, double prijs) {
        Transactie transactie = new Transactie(LocalDateTime.now(), verkoper, cryptomunt, prijs, aantal, koper, TRANSACTION_FEE);
        slaTransactieOp(transactie);
        return transactie;
    }

    private void setRekeningEnPortefeuilleNaTransactie(Gebruiker verkoper, Cryptomunt cryptomunt,
                                                       Gebruiker koper, double bedragKoper, double bedragVerkoper, double aantal) {
        koper.setRekening(rekeningService.wijzigSaldo(koper, -bedragKoper));
        verkoper.setRekening(rekeningService.wijzigSaldo(verkoper, bedragVerkoper));
        rekeningService.wijzigSaldo(Bank.getInstance(), TRANSACTION_FEE);
        assetService.wijzigAssetGebruiker(verkoper, cryptomunt, -aantal);
        assetService.wijzigAssetGebruiker(koper, cryptomunt, aantal);

    }


    /**
     * Berekent de prijs wanneer direct met de bank gehandeld wordt door het aantal te verkopen of te kopen
     * cryptomunten met de huidige koerswaarde van de cryptomunt te vermenigvuldigen
     *
     * @param cryptomunt waarmee gehandeld zal worden
     * @return de prijs
    * */
    public double berekenPrijsTransactieMetBank(Cryptomunt cryptomunt){
        return rootRepository.haalMeestRecenteCryptoWaarde(cryptomunt).getWaarde();
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
        System.out.println("Zoveel heeft hij van deze crypto: " +  verkoper + " " + assetVerkoper.getAantal());
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
        if((saldoGebruiker - bedrag) < 0 ) {
            logger.info("Saldo koper te laag voor deze transactie.");
            throw new BalanceTooLowException();
        }
    }
    public RootRepository getRootRepository() {
        return rootRepository;
    }
}


