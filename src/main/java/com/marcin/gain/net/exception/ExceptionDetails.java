package com.marcin.gain.net.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class ExceptionDetails {
    private Date timestamp;
    private int status;
    private String message;
}
