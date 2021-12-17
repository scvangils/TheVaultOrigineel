package com.example.thevault.service;

import com.example.thevault.domain.mapping.repository.RootRepository;
import com.example.thevault.domain.model.CryptoWaarde;
import com.example.thevault.domain.model.Cryptomunt;
import com.example.thevault.support.api.CryptoDagwaarde;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class CryptoWaardeService {
    private RootRepository rootRepository;

    @JsonIgnore
    private final Logger logger = LoggerFactory.getLogger(CryptoWaardeService.class);


    /*
    roept 1x per dag de methode haalCryptoWaardes op
     */
    public void main(String[] args) {
        Timer timer = new Timer();
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                haalCryptoWaardes();
            }
        };
        timer.schedule(tt, new Date(), TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS));

    }

    public void slaCryptoWaardeOp(CryptoWaarde cryptoWaarde){
        rootRepository.slaCryptoWaardeOp(cryptoWaarde);
    }

    public CryptoWaarde vindCryptoWaarde(Cryptomunt cryptomunt){
        return rootRepository.haalMeestRecenteCryptoWaarde(cryptomunt);
    }

    /*
    maakt een lijst aan van crypto's
    haalt per stuk de waarde op en slaat die met overige gegevens op in database
     */
    public void haalCryptoWaardes(){
        Cryptomunt bitcoin = new Cryptomunt(1, "bitcoin", "BTC");
        Cryptomunt ethereum = new Cryptomunt(1027, "ethereum", "ether");
        Cryptomunt solana = new Cryptomunt(5426, "solana", "sol");

        ArrayList<Cryptomunt> alleCrypto = new ArrayList<>();
        alleCrypto.add(bitcoin);
        alleCrypto.add(ethereum);
        alleCrypto.add(solana);

        for (int i = 0; i < alleCrypto.size(); i++) {
            double cryptoDagwaarde = CryptoDagwaarde.cryptoDagwaarde(alleCrypto.get(i));
            LocalDate datum = LocalDate.now();
            CryptoWaarde cryptoWaarde = new CryptoWaarde(alleCrypto.get(i).getSymbol(), alleCrypto.get(i), cryptoDagwaarde,datum);
            slaCryptoWaardeOp(cryptoWaarde);
        }
    }
}


