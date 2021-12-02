// Creation date 2-12-2021

package com.example.thevault.support.hashing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.SecureRandom;

public class SaltMaker {

    private final Logger logger = LoggerFactory.getLogger(SaltMaker.class);

    private static final int SALT_LENGTH = 8;
    private int saltLength;
    private SecureRandom secureRNG;

    public SaltMaker(int saltLength) {
        super();
        logger.info("New SaltMaker");
        this.saltLength = saltLength;
        secureRNG = new SecureRandom();
    }

    public SaltMaker() {
        this(SALT_LENGTH);
        logger.info("New SaltMaker, no-args");
    }

    public String generateSalt() {
        int tempLengte = saltLength / 2;
        byte[] arr = new byte[saltLength % 2 == 0 ? tempLengte : tempLengte + 1]; // 1 byte geeft 2 karakters, bij oneven lengte geet integer deling onderwaarde
        secureRNG.nextBytes(arr);
        String salt = ByteArrayToHexHelper.codeerHexString(arr);
        return saltLength % 2 == 0 ? salt : salt.substring(1); // als oneven is er 1 karakter teveel, haal deze weg
    }

    public void setlength(int saltLength) {
        this.saltLength = saltLength;
    }
}