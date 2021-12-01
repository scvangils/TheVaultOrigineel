// Created by carme
// Creation date 01/12/2021

package com.example.thevault.support.exceptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginException {

    private final Logger logger = LoggerFactory.getLogger(LoginException.class);

    public LoginException() {
        super();
        logger.info("New LoginException");
    }
}
