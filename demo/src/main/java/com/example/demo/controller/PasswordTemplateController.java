package com.example.demo.controller;

import com.example.demo.model.PasswordTemplate;
import com.example.demo.model.User;
import com.example.demo.model.User.Role;
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
@RequestMapping("/templates")
@AllArgsConstructor
public class PasswordTemplateController {

    private final PasswordTemplateService passwordTemplateService;
    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<PasswordTemplate> createTemplate(@RequestBody PasswordTemplate template) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findByUsername(userDetails.getUsername());
        template.setUser(user);
        PasswordTemplate savedTemplate = passwordTemplateService.createTemplate(template);
        return new ResponseEntity<>(savedTemplate, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PasswordTemplate>> getTemplates() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findByUsername(userDetails.getUsername());
        List<PasswordTemplate> templates = passwordTemplateService.getTemplatesByUserId(user.getId());
        return new ResponseEntity<>(templates, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PasswordTemplate> getTemplateById(@PathVariable Long id) {
        PasswordTemplate template = passwordTemplateService.getTemplateById(id);
        return new ResponseEntity<>(template, HttpStatus.OK);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<PasswordTemplate> updateTemplate(@PathVariable Long id,
            @RequestBody PasswordTemplate template) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findByUsername(userDetails.getUsername());

        PasswordTemplate existingTemplate = passwordTemplateService.findById(id)
                .orElseThrow(() -> new RuntimeException("Template not found"));

        if (!existingTemplate.getUser().getId().equals(user.getId()) && !user.getRole().equals(Role.ADMIN)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        existingTemplate.setLength(template.getLength());
        existingTemplate.setIncludeLowercase(template.isIncludeLowercase());
        existingTemplate.setIncludeUppercase(template.isIncludeUppercase());
        existingTemplate.setIncludeNumbers(template.isIncludeNumbers());
        existingTemplate.setIncludeSymbols(template.isIncludeSymbols());

        return ResponseEntity.ok(passwordTemplateService.updateTemplate(existingTemplate));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTemplate(@PathVariable Long id) {
        passwordTemplateService.deleteTemplate(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
