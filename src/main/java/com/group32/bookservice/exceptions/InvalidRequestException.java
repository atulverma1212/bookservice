package com.group32.bookservice.exceptions;


public class InvalidRequestException extends BookServiceBaseException {

    public InvalidRequestException(String message) {
        super(message);
    }

}
