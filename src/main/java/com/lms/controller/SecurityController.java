package com.lms.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.lms.dto.PersonRequest;
import com.lms.repo.PersonRepository;

@RestController
@RequestMapping("/security")
@Validated
public class SecurityController {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody PersonRequest request) {
        // Check if username is already taken
        if (personRepository.getPersonByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Username already exists");
        }

        // Encode the password before saving to the database
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        request.setPassword(encodedPassword);

        personRepository.addPerson(request);

        return ResponseEntity.ok("User registered successfully");
    }
}

