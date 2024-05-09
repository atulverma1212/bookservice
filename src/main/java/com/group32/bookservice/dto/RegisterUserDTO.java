package com.group32.bookservice.dto;

import lombok.Data;

@Data
public class RegisterUserDTO {
    private String email;
    private String password;
    private String fullName;
    private String username;

}
