// Created by E.S. Olthof
// Creation date 15-12-2020

package com.example.thevault.service;

import com.example.thevault.domain.mapping.repository.RootRepository;
import com.example.thevault.domain.model.Asset;
import com.example.thevault.domain.model.Cryptomunt;
import com.example.thevault.domain.model.Klant;
import com.example.thevault.domain.model.Transactie;
import com.example.thevault.support.exceptions.BalanceTooLowException;
import com.example.thevault.support.exceptions.NotEnoughCryptoException;
import net.minidev.json.annotate.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactieService {

    private RootRepository rootRepository;

    @JsonIgnore
    private final Logger logger = LoggerFactory.getLogger(RekeningService.class);

    @Autowired
    public TransactieService(RootRepository rootRepository) {
        super();
        this.rootRepository = rootRepository;
        logger.info("New TransactieService.");
    }

    /**
     * TODO nog afmaken zodat de juiste exceptions worden gegeven en de rekening wordt geupdated
     *
     * Maak een transactie op basis van een bedrag, cryptomunt, koper en verkoper en een datum.
     * Als de koper niet genoeg saldo heeft of wanneer de verkoper niet het aantal cryptomunten
     * heeft dat deze wilt verkopen geeft de methode een foutmelding
     *
     * @param momentTransactie datum en tijd transactie
     * @param verkoper verkoper van cryptomunt
     * @param cryptomunt het type cryptomunt
     * @param bedrag het bedrag dat voor de cryptomunt betaald moet worden
     * @param aantal de hoeveelheid cryptomunt
     * @param koper de koper van de cryptomunt in deze transactie
     *
     * @return boolean (true als de transactie geslaagd is)
    * */
    public Transactie maakTransactie(OffsetDateTime momentTransactie, Klant verkoper, Cryptomunt cryptomunt,
                                  double bedrag, double aantal, Klant koper) {
        Transactie transactie = new Transactie();

        // handel eventuele exceptions af
        saldoTooLowExceptionHandler(koper, bedrag);
        notEnoughCryptoExceptionHandler(verkoper, cryptomunt, aantal);


        return transactie;
    }


    /**
     * TODO exceptionhandler afmaken
     *
     * Checkt of de verkoper genoeg cryptomunten heeft om de transactie
     * te kunnen sluiten
     *
     * @param verkoper klant-object verkoper
     * @param cryptomunt Cryptomunt-object
     * @param aantal double hoeveelheid cryptomunt
     *
     * */
    public void notEnoughCryptoExceptionHandler (Klant verkoper, Cryptomunt cryptomunt, double aantal) throws NotEnoughCryptoException {
        double aantalCryptoVerkoper;

        List<Asset> assetsVerkoper = verkoper.getPortefeuille();
        for (Asset asset: assetsVerkoper) {
            if(asset.getCryptomunt() == cryptomunt){
                if(asset.getAantal() < aantal){
                    throw new NotEnoughCryptoException();
                }
            }
        }
        if (!verkoper.getPortefeuille().contains(cryptomunt)) {
            logger.info("Cryptomunt niet beschikbaar bij verkoper");
            throw new NotEnoughCryptoException();
        }
    }

    /**
     * Controleert of het saldo van de koper toereikend is voor de
     * transactie
     *
     * @param koper
     * @param bedrag
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


