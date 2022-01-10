package com.iscte.mei.ads.schedules.api.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.NoSuchElementException;

@ControllerAdvice
public class ExceptionController {

    private final Logger logger = LoggerFactory.getLogger(ExceptionController.class);

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(
            value = HttpStatus.BAD_REQUEST
    )
    public void illegalArgumentException(IllegalArgumentException ex) {
        logger.error("Exception thrown: ", ex);
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(
            value = HttpStatus.NOT_FOUND
    )
    public void noSuchElementException(NoSuchElementException ex) {
        logger.error("Exception thrown: ", ex);
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(
            value = HttpStatus.CONFLICT
    )
    public void illegalStateException(IllegalStateException ex) {
        logger.error("Exception thrown: ", ex);
    }

}
