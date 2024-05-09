package com.group32.bookservice.exceptions;

import lombok.Data;


public class EntityAlreadyExistsException extends BookServiceBaseException {

    public EntityAlreadyExistsException(String message) {
        super(message);
    }

}
