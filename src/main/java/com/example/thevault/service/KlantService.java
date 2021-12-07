// Created by carme
// Creation date 01/12/2021

package com.example.thevault.service;

import com.example.thevault.domain.mapping.repository.RootRepository;
import com.example.thevault.domain.model.Asset;
import com.example.thevault.domain.model.Klant;
import com.example.thevault.support.BSNvalidator;
import com.example.thevault.support.exceptions.IncorrectBSNException;
import com.example.thevault.support.exceptions.RegistrationFailedException;
import com.example.thevault.support.hashing.BCryptWachtwoordHash;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.List;

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

    /**
     * Deze methode zoekt of er in de database al een klant bestaat met deze gebruikersnaam
     * en maakt eventueel een klant-object aan op nasis van de teruggestuurde gegevens
     *
     * @param gebruikersnaam gebruikersnaam van een (mogelijke) klant die uniek moet zijn
     * @return klant-object op basis van gegevens uit de database of null indien gebruikersnaam niet gevonden is
     */
    public Klant vindKlantByGebruikersnaam(String gebruikersnaam){
        return rootRepository.vindKlantByGebruikersnaam(gebruikersnaam);
    }

    /**
     * Deze methode zoekt of er in de database al een klant bestaat met deze gebruikerId
     * en maakt eventueel een klant-object aan op nasis van de teruggestuurde gegevens
     *
     * @param gebruikerId gebruikerId van een (mogelijke) klant die uniek moet zijn
     * @return klant-object op basis van gegevens uit de database of null indien gebruikerId niet gevonden is
     */
    public Klant vindKlantbyId(String gebruikerId){
        return vindKlantbyId(gebruikerId);
    }

    /**
     * Deze methode probeert een nieuwe klant te registreren.
     * Als de gegevens correct zijn ingevuld en de gebruikersnaam nog niet bestaat,
     * wordt het wachtwoord eerst gehasht en daarna versleuteld.
     * Vervolgens wordt de klant opgeslagen in de database.
     *
     * @param klant een Klant-object is wordt aangemaakt op basis van ingevoerde gegevens
     * @return het klant-object met het gealtereerde wachtwoord
     */
    public Klant registreerKlant(Klant klant){
        if(!BSNvalidator.bsnValideren(klant.getBsn())){
            throw new IncorrectBSNException();
        }
        //TODO nakijken of datum check nodig heeft
        //TODO leeftijd minimaal 18 checken
        if(vindKlantByGebruikersnaam(klant.getGebruikersnaam()) != null){
            throw new RegistrationFailedException();
        }
        String teHashenWachtwoord = klant.getWachtwoord();
        String gehashtWachtwoord = BCryptWachtwoordHash.hashWachtwoord(teHashenWachtwoord); // hash wachtwoord
        gehashtWachtwoord = Base64.encodeBase64String(gehashtWachtwoord.getBytes(StandardCharsets.UTF_8)); // versleutel gehasht wachtwoord
        klant.setWachtwoord(gehashtWachtwoord);
        rootRepository.slaKlantOp(klant);
        return klant;
    }

    public RootRepository getRootRepository() {
        return rootRepository;
    }
}
