package com.example.thevault.service;

import com.example.thevault.domain.mapping.repository.RootRepository;
import com.example.thevault.domain.model.CryptoWaarde;
import com.example.thevault.domain.model.Cryptomunt;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CryptoWaardeService {
    private RootRepository rootRepository;

    @JsonIgnore
    private final Logger logger = LoggerFactory.getLogger(CryptoWaardeService.class);

    //TODO void wijzigen in CryptoWaarde
    public void slaCryptoWaardeOp(CryptoWaarde cryptoWaarde){
        rootRepository.slaCryptoWaardeOp(cryptoWaarde);
    }

    public CryptoWaarde vindCryptoWaarde(Cryptomunt cryptomunt){
        return rootRepository.haalMeestRecenteCryptoWaarde(cryptomunt);
    }


}


