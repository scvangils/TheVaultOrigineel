// Created by carme
// Creation date 01/12/2021

package com.example.thevault.service;

import com.example.thevault.domain.mapping.repository.RootRepository;
import com.example.thevault.domain.model.Asset;
import com.example.thevault.domain.model.Cryptomunt;
import com.example.thevault.domain.model.Klant;
import com.example.thevault.support.BSNvalidator;
import com.example.thevault.support.exceptions.IncorrectBSNException;
import com.example.thevault.support.exceptions.IncorrectFormatException;
import com.example.thevault.support.exceptions.RegistrationFailedException;
import com.example.thevault.support.hashing.BCryptWachtwoordHash;
import com.example.thevault.support.hashing.HashHelper;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class KlantService {

    private RootRepository rootRepository;
    private final Logger logger = LoggerFactory.getLogger(KlantService.class);

    @Autowired
    public KlantService(RootRepository rootRepository) {
        super();
        this.rootRepository = rootRepository;
        logger.info("New KlantService.");
    }

    public Klant vindKlantByUsername(String username){
        return rootRepository.vindKlantByUsername(username);
    }

    public Klant registreerKlant(Klant klant){ // TODO void van maken?
        if(!BSNvalidator.bsnValideren(klant.getBsn())){
            throw new IncorrectBSNException();
        }
        //TODO nakijken of datum check nodig heeft
        //TODO leeftijd minimaal 18 checken
        if(vindKlantByUsername(klant.getGebruikersnaam()) != null){
            throw new RegistrationFailedException();
        }
        String teHashenWachtwoord = klant.getWachtwoord();
        String gehashtWachtwoord = BCryptWachtwoordHash.hashWachtwoord(teHashenWachtwoord); // hash wachtwoord
        gehashtWachtwoord = Base64.encodeBase64String(gehashtWachtwoord.getBytes(StandardCharsets.UTF_8)); // versleutel gehasht wachtwoord
        klant.setWachtwoord(gehashtWachtwoord);
        System.out.println(gehashtWachtwoord);
        rootRepository.slaKlantOp(klant);
        return klant;
    }

    public RootRepository getRootRepository() {
        return rootRepository;
    }
}
