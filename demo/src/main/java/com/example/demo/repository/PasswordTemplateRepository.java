package com.example.demo.repository;

import com.example.demo.model.PasswordTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PasswordTemplateRepository extends JpaRepository<PasswordTemplate, Long> {
    List<PasswordTemplate> findByUserId(Long userId);
}
