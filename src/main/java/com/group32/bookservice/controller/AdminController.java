package com.group32.bookservice.controller;

import com.group32.bookservice.dto.RegisterUserDTO;
import com.group32.bookservice.exceptions.EntityAlreadyExistsException;
import com.group32.bookservice.model.User;
import com.group32.bookservice.repository.UserRepository;
import com.group32.bookservice.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RequestMapping("/admins")
@RestController
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;
    private final UserRepository userRepository;

    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<User> createAdministrator(@RequestBody RegisterUserDTO registerUserDto) {
        Optional<User> existingUser = userRepository.findByusername(registerUserDto.getUsername());

        if(existingUser.isPresent()) {
            throw new EntityAlreadyExistsException("User already exists");
        }

        User createdAdmin = userService.createAdmin(registerUserDto);
        return ResponseEntity.ok(createdAdmin);
    }
}
