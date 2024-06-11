package com.example.demo.service;

import com.example.demo.model.Password;
import com.example.demo.model.PasswordTemplate;
import com.example.demo.repository.PasswordRepository;

import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Service
@AllArgsConstructor
public class PasswordService {

    @Autowired
    private final PasswordRepository passwordRepository;

    @Transactional
    public Password savePassword(Password password) {
        return passwordRepository.save(password);
    }

    @Transactional
    public void deletePassword(Long id) {
        passwordRepository.deleteById(id);
    }

    public List<Password> getPasswordsByUserId(Long userId) {
        return passwordRepository.findByUserId(userId);
    }

    public Password findById(Long id) {
        return passwordRepository.findById(id).orElse(null);
    }

    public String generatePassword(PasswordTemplate template) {
        String upperCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";
        String symbols = "!@#$%^&*()-_+=<>?";

        StringBuilder characterSet = new StringBuilder();
        if (template.isIncludeUppercase()) {
            characterSet.append(upperCaseLetters);
        }
        if (template.isIncludeLowercase()) {
            characterSet.append(lowerCaseLetters);
        }
        if (template.isIncludeNumbers()) {
            characterSet.append(numbers);
        }
        if (template.isIncludeSymbols()) {
            characterSet.append(symbols);
        }

        Random random = new Random();
        StringBuilder password = new StringBuilder(template.getLength());
        for (int i = 0; i < template.getLength(); i++) {
            int index = random.nextInt(characterSet.length());
            password.append(characterSet.charAt(index));
        }

        return password.toString();
    }

}
