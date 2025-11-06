package com.weatherintegration.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public com.weatherintegration.model.Error handleBadRequest(BadRequestException ex) {
        return new com.weatherintegration.model.Error().code(("BAD_REQUEST")).message(ex.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public com.weatherintegration.model.Error handleNotFound(NotFoundException ex) {
        return new com.weatherintegration.model.Error().code(("NOT_FOUND")).message(ex.getMessage());
    }

    @ExceptionHandler(OpenWeatherException.class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    public com.weatherintegration.model.Error handleOpenWeatherException(OpenWeatherException ex) {
        return new com.weatherintegration.model.Error().code(("BAD_GATEWAY")).message(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public com.weatherintegration.model.Error handleGeneric(Exception ex) {
        //Todo need to log error for debug
        return new com.weatherintegration.model.Error()
                .code("INTERNAL_ERROR")
                .message("Unexpected server error");
    }
}
