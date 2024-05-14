package com.finalproject.azercell.controller;

import com.finalproject.azercell.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandling {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionDTO handle(NotFoundException exception){
        log.info("error -> {}", exception.getMessage());
        return new ExceptionDTO(exception.getMessage());
    }

    @ExceptionHandler(NotEnoughBalanceException.class)
    @ResponseStatus(HttpStatus.PAYMENT_REQUIRED)
    public ExceptionDTO handle(NotEnoughBalanceException exception){
        log.info("error -> {}", exception.getMessage());
        return new ExceptionDTO(exception.getMessage());
    }

    @ExceptionHandler(TariffCreateException.class)
    @ResponseStatus(HttpStatus.PAYMENT_REQUIRED)
    public ExceptionDTO handle(TariffCreateException exception){
        log.info("error -> {}", exception.getMessage());
        return new ExceptionDTO(exception.getMessage());
    }


    @ExceptionHandler(UserAlreadyExist.class)
    @ResponseStatus(HttpStatus.PAYMENT_REQUIRED)
    public ExceptionDTO handle(UserAlreadyExist exception){
        log.info("error -> {}", exception.getMessage());
        return new ExceptionDTO(exception.getMessage());
    }
}
