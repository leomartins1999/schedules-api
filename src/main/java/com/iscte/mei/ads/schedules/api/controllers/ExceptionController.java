package com.iscte.mei.ads.schedules.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.NoSuchElementException;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(
            value = HttpStatus.BAD_REQUEST
    )
    public void illegalArgumentException(IllegalArgumentException ex) {
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(
            value = HttpStatus.NOT_FOUND
    )
    public void noSuchElementException(NoSuchElementException ex) {
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(
            value = HttpStatus.CONFLICT
    )
    public void illegalStateException(IllegalStateException ex) {
    }

}
