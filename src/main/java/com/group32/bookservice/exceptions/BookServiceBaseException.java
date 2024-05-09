package com.group32.bookservice.exceptions;

import lombok.Data;

@Data
public class BookServiceBaseException extends RuntimeException {

    private String message;

    public BookServiceBaseException(String message) {
        this.message = message;
    }

}