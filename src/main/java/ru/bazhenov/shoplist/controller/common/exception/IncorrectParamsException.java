package ru.bazhenov.shoplist.controller.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IncorrectParamsException extends RuntimeException {
    public IncorrectParamsException(String exception) {
        super(exception);
    }
}
