package com.example.demo.service;

import com.example.demo.model.PasswordTemplate;
import com.example.demo.repository.PasswordTemplateRepository;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PasswordTemplateService {

    private final PasswordTemplateRepository passwordTemplateRepository;

    public PasswordTemplate createTemplate(PasswordTemplate template) {
        return passwordTemplateRepository.save(template);
    }

    public Optional<PasswordTemplate> findById(Long id) {
        return passwordTemplateRepository.findById(id);
    }

    public List<PasswordTemplate> getTemplatesByUserId(Long userId) {
        return passwordTemplateRepository.findByUserId(userId);
    }

    public PasswordTemplate getTemplateById(Long id) {
        return passwordTemplateRepository.findById(id).orElseThrow(() -> new RuntimeException("Template not found"));
    }

    public void deleteTemplate(Long id) {
        passwordTemplateRepository.deleteById(id);
    }

    public PasswordTemplate updateTemplate(PasswordTemplate template) {
        return passwordTemplateRepository.save(template);
    }
}
