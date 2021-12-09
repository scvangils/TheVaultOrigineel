// Created by carme
// Creation date 30/11/2021

package com.example.thevault.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Cryptomunt {

    private int Id;
    private String name;
    private String symbol;
    private double price;

    @JsonIgnore
    private final Logger logger = LoggerFactory.getLogger(Cryptomunt.class);

    public Cryptomunt() {
        super();
        logger.info("New Cryptomunt, no args constructor");
    }


    public Cryptomunt (int cryptomuntId, String name, String afkorting, double price){
        this.Id = cryptomuntId;
        this.name = name;
        this.symbol = afkorting;
        this.price = price;
        logger.info("Cryptomunt:" + this);
    }

    @Override
    public String toString() {
        return "Cryptomunt{" +
                "cryptomuntId=" + Id +
                ", naam='" + name + '\'' +
                ", afkorting='" + symbol + '\'' +
                ", waarde=" + price +
                '}';
    }

    public int getId() {
        return Id;
    }

    public void setId(int cryptomuntId) {
        this.Id = cryptomuntId;
    }

    public String getName() {
        return name;
    }

    public void setName(String naam) {
        this.name = naam;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String afkorting) {
        this.symbol = afkorting;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double waarde) {
        this.price = waarde;
    }

}
