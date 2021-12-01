// Created by carme
// Creation date 01/12/2021

package com.example.thevault.service;

import com.example.thevault.domain.mapping.repository.RootRepository;
import com.example.thevault.domain.model.Cryptomunt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    }

    public double geefSaldoCryptomunt(int cryptomuntId, int klantId){
        //Roept via de RootRepo informatie op vanuit de tussentabel 'portefeuille' obv combinatie klantId en cryptomuntId
        //Roept de methode 'geefHoeveelheidCryptomuntVanKlant' aan van de rootRepository
        //Zie beschrijving methode hierboven 'geefInhoudPortefeuille'
    }

    public RootRepository getRootRepository() {
        return rootRepository;
    }
}
