package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PasswordControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "newuser", password = "newpassword")
    public void testAddPassword() throws Exception {
        String passwordJson = "{\"siteUrl\":\"http://example.com\", \"password\":\"password123\"}";
        mockMvc.perform(post("/passwords/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(passwordJson))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "newuser", password = "newpassword")
    public void testGeneratePassword() throws Exception {
        mockMvc.perform(post("/passwords/generate?length=12&siteUrl=http://example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.password").isNotEmpty());
    }

    @Test
    @WithMockUser(username = "newuser", password = "newpassword")
    public void testGetUserPasswords() throws Exception {
        mockMvc.perform(get("/passwords"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}
