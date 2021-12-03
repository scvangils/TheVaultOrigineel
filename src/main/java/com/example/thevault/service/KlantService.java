// Created by carme
// Creation date 01/12/2021

package com.example.thevault.service;

import com.example.thevault.domain.mapping.repository.RootRepository;
import com.example.thevault.domain.model.Cryptomunt;
import com.example.thevault.domain.model.Klant;
import com.example.thevault.support.BSNvalidator;
import com.example.thevault.support.exceptions.IncorrectBSNException;
import com.example.thevault.support.exceptions.IncorrectFormatException;
import com.example.thevault.support.exceptions.RegistrationFailedException;
import com.example.thevault.support.hashing.HashHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    public Klant registreerKlant(Klant klant){
        if(!BSNvalidator.bsnValideren(klant.getBsn())){
            throw new IncorrectBSNException();
        }
        //TODO nakijken of datum check nodig heeft
        if(vindKlantByUsername(klant.getGebruikersnaam()) != null){
            throw new RegistrationFailedException();
        }
        String teHashenWachtwoord = klant.getWachtwoord();
        //TODO hier hash en salt toevoegen
         String gehashtWachtwoord = HashHelper.hashHelper(teHashenWachtwoord);
     //   gehashtWachtwoord = Base64.encodeBase64String(gehashtWachtwoord.getBytes(StandardCharsets.UTF_8));
     //   gehashtWachtwoord = new String(Base64.decodeBase64(gehashtWachtwoord));
        klant.setWachtwoord(gehashtWachtwoord);
       rootRepository.slaKlantOp(klant);
        // TODO rekening aanmaken
        return null;
    }
    // kijkt na of BSN het juiste format heeft, nodig voor rechtstreekse API call


    /**
     * @Author: Carmen
     * Opmerking: Deze twee methodes had ik aangemaakt in PortefeuilleDaoInterface, maar volgens mij
     * horen ze thuis in de service, omdat dit informatie geeft die je wilt aanvragen via de klant
     * waarbij we dan de get/put/update etc van portefeuilleDAO kunnen aanroepen.
     * Ik heb ze hier neergezet om ze niet te vergeten, wanneer we met de methodes van service bezig gaan
     * kunnen we hier verder aan werken.
     */

    public Map<Cryptomunt,Double> geefInhoudPortefeuille(int klantId){
        //Roept via de RootRepo informatie op vanuit de tussentabel 'portefeuille' obv de klantId
        //Roept de methode 'geefAlleCryptomuntenVanKlant' aan van de rootRepository
        //Hieronder relevant voorbeeld van kwebbelweb
        /*Optional<Member> memberOption = rootRepository.findMemberById(memberId);
        if (memberOption.isPresent()) {
            Member member = memberOption.get();
            List<Message> messages = rootRepository.findAllMessagesFromAuthor(member);
            return messages;
        }
        return new ArrayList<>();*/
        return null;
    }

    public double geefSaldoCryptomunt(int cryptomuntId, int klantId){
        //Roept via de RootRepo informatie op vanuit de tussentabel 'portefeuille' obv combinatie klantId en cryptomuntId
        //Roept de methode 'geefHoeveelheidCryptomuntVanKlant' aan van de rootRepository
        //Zie beschrijving methode hierboven 'geefInhoudPortefeuille'
        return 0.0;
    }

    public RootRepository getRootRepository() {
        return rootRepository;
    }
}
