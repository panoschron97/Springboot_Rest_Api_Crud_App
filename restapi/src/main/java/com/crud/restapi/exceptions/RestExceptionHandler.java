package com.crud.restapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler
{

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(NotFoundException exception)
    {
        ErrorResponse errorresponse = new ErrorResponse();
        errorresponse.setStatus(HttpStatus.NOT_FOUND.value());
        errorresponse.setMessage(exception.getMessage());
        errorresponse.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(errorresponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(Exception exception)
    {
        ErrorResponse errorresponse = new ErrorResponse();
        errorresponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorresponse.setMessage(exception.getMessage());
        errorresponse.setMessage("Failed to perform the operation!");
        errorresponse.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(errorresponse, HttpStatus.BAD_REQUEST);
    }

}
