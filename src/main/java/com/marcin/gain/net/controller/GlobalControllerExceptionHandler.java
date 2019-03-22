package com.marcin.gain.net.controller;

import com.marcin.gain.net.exception.ClientException;
import com.marcin.gain.net.exception.ExceptionDetails;
import com.marcin.gain.net.exception.InvalidDataException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@ControllerAdvice(basePackages = "com.marcin.gain.net.controller")
@RestController
public class GlobalControllerExceptionHandler {
    @ExceptionHandler(ClientException.class)
    public final ResponseEntity handleClientExceptions(ClientException exception) {
        return new ResponseEntity<>(new ExceptionDetails(new Date(),
                HttpStatus.BAD_REQUEST.value(),
                exception.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidDataException.class)
    public final ResponseEntity handleInvalidDataExceptions(InvalidDataException exception) {
        return new ResponseEntity<>(new ExceptionDetails(new Date(),
                                                        HttpStatus.BAD_REQUEST.value(),
                                                        exception.getMessage()),
                                                        HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public final ResponseEntity handleAllRuntimeExceptions() {
        return new ResponseEntity<>(new ExceptionDetails(new Date(),
                                                        HttpStatus.BAD_REQUEST.value(),
                                                "Bad request. Something went wrong. Please check parameters."),
                                                        HttpStatus.BAD_REQUEST);
    }
}
