package com.doruk.bartu.recipes;

import com.doruk.bartu.recipes.auth.LoginRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import org.junit.jupiter.api.BeforeEach;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private com.doruk.bartu.recipes.recipe.RecipeRepository recipeRepository;

    @Autowired
    private com.doruk.bartu.recipes.user.UserRepository userRepository;

   @BeforeEach
    public void setup() {
        recipeRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void testRegisterAndLoginFlow() throws Exception {

        LoginRequest registerReq = new LoginRequest("integration@test.com", "password123");

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerReq)))
                .andExpect(status().isOk());


        LoginRequest loginReq = new LoginRequest("integration@test.com", "password123");

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginReq)))
                .andExpect(status().isOk());


        LoginRequest wrongReq = new LoginRequest("integration@test.com", "WRONG_PASS");

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(wrongReq)))
                .andExpect(status().isUnauthorized());
    }
}