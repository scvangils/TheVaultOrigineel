//20211201WB random IBAN

package com.example.thevault.service;

import com.example.thevault.domain.mapping.repository.RootRepository;
import com.example.thevault.domain.model.Klant;
import com.example.thevault.domain.model.Rekening;
import com.example.thevault.support.exceptions.UserNotExistsException;
import org.iban4j.CountryCode;
import org.iban4j.Iban;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RekeningService {

    private RootRepository rootRepository;
    private final Logger logger = LoggerFactory.getLogger(RekeningService.class);

    private final Double STARTSALDO = 1000.0;
    private double Saldo;
    private Iban iban;

    @Autowired
    public RekeningService(RootRepository rootRepository) {
        super();
        this.rootRepository = rootRepository;
        logger.info("Nieuwe RekeningService.");
    }

    public Iban creeerIban(){
        Iban iban = new Iban.Builder().countryCode(CountryCode.NL).bankCode("TVLT").buildRandom();
        return iban;
    }

    public Rekening creeerRekening(Klant klant) {
        String iban = creeerIban().toString();
<<<<<<< HEAD
        Rekening rekening = new Rekening(iban, STARTSALDO);
        klant.setRekening(rekening);
        rekening.setKlant(klant);
=======
        Rekening rekening = new Rekening(klant.getGebruikerID(), iban, STARTSALDO, klant);
        System.out.println(rekening);
>>>>>>> 9cfd3341e2797939b367d96d3cb98ec3afc80b8a
        return rekening;
    }

    public void slaRekeningOp(Rekening rekening){
        rootRepository.slaRekeningOp(rekening);
    }

    public Rekening vindRekeningVanKlant (Klant klant) throws UserNotExistsException {
        if (klant == null){
            throw new UserNotExistsException();
        }
        if (rootRepository.vindKlantByUsername(klant.getNaam()) == null ){
            throw new UserNotExistsException();
        }
        return rootRepository.vindRekeningVanKlant(klant);
    }

    public double vraagSaldoOpVanKlant(Klant klant) throws UserNotExistsException{
        return vindRekeningVanKlant(klant).getSaldo();
    }

    public void wijzigSaldoVanKlant(Klant klant, double bedrag){
        rootRepository.wijzigSaldoVanKlant(klant, bedrag);
    }

    public RootRepository getRootRepository() {
        return rootRepository;
    }
}
