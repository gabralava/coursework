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
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "newuser", password = "newpassword")
    public void testGeneratePassword() throws Exception {
        mockMvc.perform(post("/passwords/generate?length=12&siteUrl=http://example.com"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.password").isNotEmpty());
    }

    @Test
    @WithMockUser(username = "newuser", password = "newpassword")
    public void testGetUserPasswords() throws Exception {
        mockMvc.perform(get("/passwords"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }


    @Test
    @WithMockUser(username = "newuser", password = "newpassword")
    public void testUpdatePassword() throws Exception {
        String passwordJson = "{\"siteUrl\":\"http://example.com\", \"password\":\"newpassword\"}";
        mockMvc.perform(put("/passwords/edit/12")
                .contentType(MediaType.APPLICATION_JSON)
                .content(passwordJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.password").value("newpassword"))
                .andExpect(jsonPath("$.siteUrl").value("http://example.com"));
    }

    @Test
    @WithMockUser(username = "newuser", password = "newpassword")
    public void testDeletePassword() throws Exception {
        mockMvc.perform(delete("/passwords/delete/5"))
                .andExpect(status().isNoContent());
    }
}
