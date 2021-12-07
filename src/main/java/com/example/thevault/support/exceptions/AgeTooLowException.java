// Created by S.C. van Gils
// Creation date 7-12-2021

package com.example.thevault.support.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


//TODO message variabel maken
@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE, reason = "Je moet minimaal 18 zijn om een rekening te openen.")
public class AgeTooLowException extends RuntimeException{

}
