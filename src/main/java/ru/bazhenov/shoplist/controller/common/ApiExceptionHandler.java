package ru.bazhenov.shoplist.controller.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.bazhenov.shoplist.controller.common.exception.IncorrectParamsException;
import ru.bazhenov.shoplist.controller.common.exception.RecordAlreadyExistException;
import ru.bazhenov.shoplist.controller.common.exception.RecordNotFoundException;
import ru.bazhenov.shoplist.controller.common.response.ApiError;
import ru.bazhenov.shoplist.controller.common.response.ApiResponse;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ApiResponse<Object>> handleAllExceptions(Exception ex, WebRequest request) {
        return getErrorResponse(ex, "Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RecordNotFoundException.class)
    public final ResponseEntity<ApiResponse<Object>> handleRecordNotFoundException(RecordNotFoundException ex, WebRequest request) {
        return getErrorResponse(ex, "Record Not Found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RecordAlreadyExistException.class)
    public final ResponseEntity<ApiResponse<Object>> handleRecordAlreadyExistException(RecordAlreadyExistException ex, WebRequest request) {
        return getErrorResponse(ex, "Record Already Exist", HttpStatus.CONFLICT);
    }

    @ExceptionHandler(IncorrectParamsException.class)
    public final ResponseEntity<ApiResponse<Object>> handleValidationException(IncorrectParamsException ex, WebRequest request) {
        return getErrorResponse(ex, "Incorrect Request Params", HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ApiResponse<Object>> getErrorResponse(Exception ex, String message, HttpStatus status) {
        ApiError error = new ApiError(message, ex.getLocalizedMessage());
        ApiResponse<Object> response = new ApiResponse<>(null, error);
        return new ResponseEntity<>(response, status);
    }
}