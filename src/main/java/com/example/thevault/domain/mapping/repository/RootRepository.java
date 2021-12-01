// Created by carme
// Creation date 01/12/2021

package com.example.thevault.domain.mapping.repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RootRepository {

    private final Logger logger = LoggerFactory.getLogger(RootRepository.class);

    public RootRepository() {
        super();
        logger.info("New RootRepository");
    }
}
