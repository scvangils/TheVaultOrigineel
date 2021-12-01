//20211201WB random IBAN

package com.example.thevault.service;

import org.iban4j.CountryCode;
import org.iban4j.Iban;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class RekeningService {

    private final Logger logger = LoggerFactory.getLogger(RekeningService.class);

    private double Saldo;
    private Iban iban;



    public Iban createIban(){
        Iban iban = new Iban.Builder().countryCode(CountryCode.NL).bankCode("TVLT").buildRandom();
        return iban;
    }

}
