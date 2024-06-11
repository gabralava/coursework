package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GeneratePasswordRequest {
    private Long templateId;
    private String siteUrl;
}
