package com.example.demo.service;

import com.example.demo.model.Password;
import com.example.demo.repository.PasswordRepository;

import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.List;

@Service
@AllArgsConstructor
public class PasswordService {

    @Autowired
    private final PasswordRepository passwordRepository;

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+";
    private static final SecureRandom RANDOM = new SecureRandom();

    @Transactional
    public Password savePassword(Password password) {
        return passwordRepository.save(password);
    }

    public List<Password> getPasswordsByUserId(Long userId) {
        return passwordRepository.findByUserId(userId);
    }

    public String generatePassword(int length) {
        if (length < 1)
            throw new IllegalArgumentException("Password length must be greater than 0");

        StringBuilder password = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            password.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }
        return password.toString();
    }

}
