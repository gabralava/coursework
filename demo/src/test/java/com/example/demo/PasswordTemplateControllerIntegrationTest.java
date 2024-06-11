package com.example.demo;

import com.example.demo.model.PasswordTemplate;
import com.example.demo.model.User;
import com.example.demo.repository.PasswordTemplateRepository;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PasswordTemplateControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PasswordTemplateRepository passwordTemplateRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
        // Убедимся, что база данных пуста перед каждым тестом
        passwordTemplateRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "newuser", password = "newpassword")
    public void testCreateTemplate() throws Exception {
        // Подготовка данных для запроса
        PasswordTemplate template = new PasswordTemplate();
        template.setLength(10);
        template.setIncludeLowercase(true);
        template.setIncludeUppercase(true);
        template.setIncludeNumbers(true);
        template.setIncludeSymbols(true);

        // Выполнение запроса
        ResultActions result = mockMvc.perform(post("/templates/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.toJson(template)));

        // Проверка ответа
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.length").value(10))
                .andExpect(jsonPath("$.includeLowercase").value(true))
                .andExpect(jsonPath("$.includeUppercase").value(true))
                .andExpect(jsonPath("$.includeNumbers").value(true))
                .andExpect(jsonPath("$.includeSymbols").value(true));
    }

    @Test
    @WithMockUser(username = "newuser", password = "newpassword")
    public void testUpdateTemplate() throws Exception {
        // Подготовка данных для запроса
        User user = new User();
        user.setUsername("newuser");
        user.setEmail("newuser@example.com");
        userRepository.save(user);

        PasswordTemplate template = new PasswordTemplate();
        template.setLength(12);
        template.setIncludeLowercase(true);
        template.setIncludeUppercase(false);
        template.setIncludeNumbers(true);
        template.setIncludeSymbols(false);
        template.setUser(user);
        passwordTemplateRepository.save(template);

        // Обновление данных шаблона
        template.setLength(15);
        template.setIncludeUppercase(true);

        // Выполнение запроса
        ResultActions result = mockMvc.perform(put("/templates/update/{id}", template.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.toJson(template)));

        // Проверка ответа
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(template.getId()))
                .andExpect(jsonPath("$.length").value(15))
                .andExpect(jsonPath("$.includeLowercase").value(true))
                .andExpect(jsonPath("$.includeUppercase").value(true))
                .andExpect(jsonPath("$.includeNumbers").value(true))
                .andExpect(jsonPath("$.includeSymbols").value(false));
    }

    // Другие тесты для методов шаблона генерации паролей

}
