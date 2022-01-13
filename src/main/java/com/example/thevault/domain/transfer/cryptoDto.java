//Wibul 220220113
//DTO moet alle crypto's doorgeven met de recente koers

//hoe zorg ik ervoor dat alle crypto's in de DTO komen?

package com.example.thevault.domain.transfer;


import com.example.thevault.domain.model.CryptoWaarde;
import com.example.thevault.domain.model.Cryptomunt;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class cryptoDto {
    @JsonIgnore
    private final Logger logger = LoggerFactory.getLogger(cryptoDto.class);

    private String cryptoAfkorting;
    private double cryptoKoers;

    public cryptoDto(){
        super();
        logger.info("new cryptoDTO no args");
    }

    public cryptoDto(CryptoWaarde cryptoWaarde){
        this.cryptoAfkorting = cryptoWaarde.getCryptomunt().getName();
        this.cryptoKoers = cryptoWaarde.getWaarde();
    }



}
