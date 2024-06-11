package com.example.demo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.demo.model.Password;
import com.example.demo.model.PasswordTemplate;
import com.example.demo.model.User;
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
        User user = new User();
        user.setId(1L);

        PasswordTemplate template = new PasswordTemplate();
        template.setLength(8);
        template.setIncludeLowercase(true);
        template.setIncludeNumbers(false);
        template.setIncludeSymbols(true);
        template.setIncludeUppercase(false);
        template.setUser(user);

        String password = passwordService.generatePassword(template);
        assertNotNull(password);
        assertEquals(8, password.length());
    }
}
