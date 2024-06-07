package com.example.demo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.demo.model.Password;
import com.example.demo.repository.PasswordRepository;
import com.example.demo.service.PasswordService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

public class PasswordServiceTest {

    @InjectMocks
    private PasswordService passwordService;

    @Mock
    private PasswordRepository passwordRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSavePassword() {
        Password password = new Password();
        when(passwordRepository.save(password)).thenReturn(password);
        Password savedPassword = passwordService.savePassword(password);
        assertEquals(password, savedPassword);
    }

    @Test
    public void testGetPasswordsByUserId() {
        Long userId = 1L;
        List<Password> passwords = List.of(new Password());
        when(passwordRepository.findByUserId(userId)).thenReturn(passwords);
        List<Password> result = passwordService.getPasswordsByUserId(userId);
        assertEquals(passwords, result);
    }

    @Test
    public void testGeneratePassword() {
        String password = passwordService.generatePassword(10);
        assertNotNull(password);
        assertEquals(10, password.length());
    }
}
