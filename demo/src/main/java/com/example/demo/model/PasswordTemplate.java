package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "password_templates")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PasswordTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int length;

    @Column(nullable = false)
    private boolean includeLowercase;

    @Column(nullable = false)
    private boolean includeUppercase;

    @Column(nullable = false)
    private boolean includeNumbers;

    @Column(nullable = false)
    private boolean includeSymbols;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
}
