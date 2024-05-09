package com.group32.bookservice.exceptions;


public class EntityNotFoundException extends BookServiceBaseException {

    public EntityNotFoundException(String message) {
        super(message);
    }

}
