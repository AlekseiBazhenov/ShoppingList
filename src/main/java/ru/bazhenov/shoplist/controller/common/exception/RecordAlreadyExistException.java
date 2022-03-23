package ru.bazhenov.shoplist.controller.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class RecordAlreadyExistException extends RuntimeException {
    public RecordAlreadyExistException(String exception) {
        super(exception);
    }
}
