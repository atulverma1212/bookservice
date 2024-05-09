package com.group32.bookservice.services;


import com.group32.bookservice.dto.LoginRequest;
import com.group32.bookservice.dto.RegisterUserDTO;
import com.group32.bookservice.exceptions.EntityAlreadyExistsException;
import com.group32.bookservice.model.Role;
import com.group32.bookservice.model.RoleEnum;
import com.group32.bookservice.model.User;
import com.group32.bookservice.repository.RoleRepository;
import com.group32.bookservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;


    public User signup(RegisterUserDTO input) {

        Optional<User> existingUser = userRepository.findByusername(input.getUsername());
        if(existingUser.isPresent()) {
            throw new EntityAlreadyExistsException("User already exists");
        }

        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.USER);

        if (optionalRole.isEmpty()) {
            return null;
        }

        User user = new User();
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        user.setUsername(input.getUsername());
        user.setRole(optionalRole.get());
        return userRepository.save(user);
    }

    public User authenticate(LoginRequest input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getUsername(),
                        input.getPassword()
                )
        );

        return userRepository.findByusername(input.getUsername())
                .orElseThrow();
    }
}
