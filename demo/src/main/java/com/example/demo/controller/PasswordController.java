package com.example.demo.controller;

import com.example.demo.dto.GeneratePasswordRequest;
import com.example.demo.model.Password;
import com.example.demo.model.PasswordTemplate;
import com.example.demo.model.User;
import com.example.demo.model.User.Role;
import com.example.demo.service.PasswordService;
import com.example.demo.service.PasswordTemplateService;
import com.example.demo.service.UserService;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/passwords")
@AllArgsConstructor
public class PasswordController {

    private final PasswordService passwordService;
    private final UserService userService;
    private final PasswordTemplateService passwordTemplateService;

    @PostMapping("/add")
    public ResponseEntity<Password> addPassword(@RequestBody Password password) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findByUsername(userDetails.getUsername());

        password.setUser(user);
        Password savedPassword = passwordService.savePassword(password);

        return new ResponseEntity<>(savedPassword, HttpStatus.CREATED);
    }

    @PostMapping("/generate")
    public ResponseEntity<Password> generatePassword(@RequestBody GeneratePasswordRequest request) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findByUsername(userDetails.getUsername());

        PasswordTemplate template = passwordTemplateService.findById(request.getTemplateId())
                .orElseThrow(() -> new RuntimeException("Template not found"));

        if (template == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else if (!template.getUser().getId().equals(user.getId()) && !user.getRole().equals(Role.ADMIN)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        String generatedPassword = passwordService.generatePassword(template);
        Password password = new Password();
        password.setUser(user);
        password.setSiteUrl(request.getSiteUrl());
        password.setPassword(generatedPassword);

        Password savedPassword = passwordService.savePassword(password);

        return new ResponseEntity<>(savedPassword, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Password>> getUserPasswords() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findByUsername(userDetails.getUsername());

        List<Password> passwords = passwordService.getPasswordsByUserId(user.getId());
        return ResponseEntity.ok(passwords);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Password> updatePassword(@PathVariable Long id, @RequestBody Password updatedPassword) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findByUsername(userDetails.getUsername());

        Password existingPassword = passwordService.findById(id);
        if (existingPassword == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else if (!existingPassword.getUser().getId().equals(user.getId()) && !user.getRole().equals(Role.ADMIN)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        existingPassword.setPassword(updatedPassword.getPassword());
        existingPassword.setSiteUrl(updatedPassword.getSiteUrl());
        Password savedPassword = passwordService.savePassword(existingPassword);

        return ResponseEntity.ok(savedPassword);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePassword(@PathVariable Long id) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findByUsername(userDetails.getUsername());

        Password existingPassword = passwordService.findById(id);
        if (existingPassword == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else if (!existingPassword.getUser().getId().equals(user.getId()) && !user.getRole().equals(Role.ADMIN)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        passwordService.deletePassword(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
