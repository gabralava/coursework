package com.example.demo;

import com.example.demo.model.PasswordTemplate;
import com.example.demo.repository.PasswordTemplateRepository;
import com.example.demo.service.PasswordTemplateService;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PasswordTemplateServiceTest {

    @InjectMocks
    private PasswordTemplateService passwordTemplateService;

    @Mock
    private PasswordTemplateRepository passwordTemplateRepository;

    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateTemplate() {
        PasswordTemplate template = new PasswordTemplate();
        when(passwordTemplateRepository.save(template)).thenReturn(template);

        PasswordTemplate savedTemplate = passwordTemplateService.createTemplate(template);

        assertNotNull(savedTemplate);
        assertEquals(template, savedTemplate);
    }

    @Test
    public void testFindById() {
        Long id = 1L;
        PasswordTemplate template = new PasswordTemplate();
        when(passwordTemplateRepository.findById(id)).thenReturn(Optional.of(template));

        Optional<PasswordTemplate> foundTemplate = passwordTemplateService.findById(id);

        assertTrue(foundTemplate.isPresent());
        assertEquals(template, foundTemplate.get());
    }

    @Test
    public void testGetTemplatesByUserId() {
        Long userId = 1L;
        List<PasswordTemplate> templates = List.of(new PasswordTemplate());
        when(passwordTemplateRepository.findByUserId(userId)).thenReturn(templates);

        List<PasswordTemplate> foundTemplates = passwordTemplateService.getTemplatesByUserId(userId);

        assertEquals(templates, foundTemplates);
    }

    @Test
    public void testGetTemplateById() {
        Long id = 1L;
        PasswordTemplate template = new PasswordTemplate();
        when(passwordTemplateRepository.findById(id)).thenReturn(Optional.of(template));

        PasswordTemplate foundTemplate = passwordTemplateService.getTemplateById(id);

        assertNotNull(foundTemplate);
        assertEquals(template, foundTemplate);
    }

    @Test
    public void testDeleteTemplate() {
        Long id = 1L;
        passwordTemplateService.deleteTemplate(id);
        verify(passwordTemplateRepository, times(1)).deleteById(id);
    }

    @Test
    public void testUpdateTemplate() {
        PasswordTemplate template = new PasswordTemplate();
        when(passwordTemplateRepository.save(template)).thenReturn(template);

        PasswordTemplate updatedTemplate = passwordTemplateService.updateTemplate(template);

        assertNotNull(updatedTemplate);
        assertEquals(template, updatedTemplate);
    }
}
