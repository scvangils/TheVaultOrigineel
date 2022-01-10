// Created by S.C. van Gils
// Creation date 8-1-2022

package com.example.thevault.support.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BankAlsTransactiePartij {

    @JsonIgnore
    private final Logger logger = LoggerFactory.getLogger(BankAlsTransactiePartij.class);

    private boolean bankAlsKoper;
    private boolean bankAlsVerkoper;

    public BankAlsTransactiePartij() {
        super();
        logger.info("New BankAlsTransactiePartij");
    }

    public BankAlsTransactiePartij(boolean bankAlsKoper, boolean bankAlsVerkoper) {
        this.bankAlsKoper = bankAlsKoper;
        this.bankAlsVerkoper = bankAlsVerkoper;
    }

    public boolean isBankAlsKoper() {
        return bankAlsKoper;
    }

    public boolean isBankAlsVerkoper() {
        return bankAlsVerkoper;
    }

}
