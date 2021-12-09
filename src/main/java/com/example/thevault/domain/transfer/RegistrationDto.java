// Created by S.C. van Gils
// Creation date 3-12-2021

/**
 * Deze klasse zorgt ervoor dat alleen de relevante informatie terug bij de klant komt
 * en dat er niet onnodige en eventueel gevoelige informatie wordt verstuurd naar de frontend.
 */

package com.example.thevault.domain.transfer;

import com.example.thevault.domain.model.Klant;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class RegistrationDto {
    @JsonIgnore
    private final Logger logger = LoggerFactory.getLogger(RegistrationDto.class);

    private String naam;
    private String gebruikersnaam;
    private String iban;
    private String postcodeEnHuisnummer;

    public RegistrationDto() {
        super();
        logger.info("New RegistrationDto no args");
    }
    public RegistrationDto(Klant klant){
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

    @Override
    public String toString() {
        return "RegistrationDto{" +
                "naam='" + naam + '\'' +
                ", gebruikersnaam='" + gebruikersnaam + '\'' +
                ", iban='" + iban + '\'' +
                ", postcodeEnHuisnummer='" + postcodeEnHuisnummer + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RegistrationDto)) return false;
        RegistrationDto registrationDto = (RegistrationDto) o;
        return naam.equals(registrationDto.naam) && gebruikersnaam.equals(registrationDto.gebruikersnaam) && iban.equals(registrationDto.iban) && postcodeEnHuisnummer.equals(registrationDto.postcodeEnHuisnummer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(naam, gebruikersnaam, iban, postcodeEnHuisnummer);
    }
}
