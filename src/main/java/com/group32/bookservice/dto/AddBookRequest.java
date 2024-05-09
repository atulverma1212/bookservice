package com.group32.bookservice.dto;

import lombok.Data;

@Data
public class AddBookRequest {
    private String title;
    private String description;
    private String author;
}
