// Created by S.C. van Gils
// Creation date 3-12-2021

package com.example.thevault.domain.transfer;

import com.example.thevault.domain.model.Klant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KlantDto {

    private final Logger logger = LoggerFactory.getLogger(KlantDto.class);

    private String naam;
    private String gebruikersnaam;
    private String iban;
    private String postcodeEnHuisnummer;

    public KlantDto() {
        super();
        logger.info("New KlantDto no args");
    }
    public KlantDto(Klant klant){
        this.naam = klant.getNaam();
        this.gebruikersnaam = klant.getGebruikersnaam();
        this.iban = klant.getRekening().getIban();
        this.postcodeEnHuisnummer = klant.getAdres().getPostcode() + " / "
                + klant.getAdres().getHuisnummer() + klant.getAdres().getToevoeging();
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getGebruikersnaam() {
        return gebruikersnaam;
    }

    public void setGebruikersnaam(String gebruikersnaam) {
        this.gebruikersnaam = gebruikersnaam;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getPostcodeEnHuisnummer() {
        return postcodeEnHuisnummer;
    }

    public void setPostcodeEnHuisnummer(String postcodeEnHuisnummer) {
        this.postcodeEnHuisnummer = postcodeEnHuisnummer;
    }
}
