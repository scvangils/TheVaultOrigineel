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

import javax.security.auth.login.LoginException;
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
        if(vindKlantByUsername(klant.getGebruikersnaam()) != null){
            throw new RegistrationFailedException();
        }
        String teHashenWachtwoord = klant.getWachtwoord();
        String gehashtWachtwoord = BCryptWachtwoordHash.hashWachtwoord(teHashenWachtwoord); // hash wachtwoord
        gehashtWachtwoord = Base64.encodeBase64String(gehashtWachtwoord.getBytes(StandardCharsets.UTF_8)); // versleutel gehasht wachtwoord
        klant.setWachtwoord(gehashtWachtwoord);
        rootRepository.slaKlantOp(klant);
        return klant;
    }

    /**
     * Wim 20211207
     * @param gebruikersNaam
     * @param wachtwoord
     * @return Klant als combinatie gebruikersnaam en wachtwoord correct is, anders geef foutmelding
     */
    public Klant valideerLogin (String gebruikersNaam, String wachtwoord) throws LoginException {
        //vraag wachtwoord op via RootRepos
        if(vindKlantByUsername(gebruikersNaam) == null){
            throw new LoginException();
        }
       if(!BCryptWachtwoordHash.verifyHash(wachtwoord, vindKlantByUsername(gebruikersNaam).getWachtwoord())){
           throw new LoginException();
       }
        return vindKlantByUsername(gebruikersNaam);
    }



    public List<Asset> geefInhoudPortefeuille(int klantId) throws SQLException {
        return rootRepository.vulPortefeuilleKlant(klantId);
    }

    public Asset geefCryptomunt(int klantId, int cryptomuntId){
        return rootRepository.geefAssetVanKlant(klantId, cryptomuntId);
    }

    public Asset slaAssetOp(int klantId, Asset asset){
        return rootRepository.slaAssetVanKlantOp(klantId, asset);
    }

    public Asset wijzigAsset(int klantId, Asset asset){
        return rootRepository.wijzigAssetVanKlant(klantId, asset);
    }

    public RootRepository getRootRepository() {
        return rootRepository;
    }
}
