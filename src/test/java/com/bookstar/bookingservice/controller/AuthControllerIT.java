package com.bookstar.bookingservice.controller;

import com.bookstar.bookingservice.base.AbstractTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class AuthControllerIT extends AbstractTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void whenCallMethodLoginThenReturnOkStatusWithCorrectResponse() throws Exception {
        String jsonRequest = objectMapper.writeValueAsString(validAuthRequest);

        mockMvc.perform(post("/api/v1/auth/login", validAuthRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());
    }

    @Test
    public void whenCallMethodLoginThenReturnUnauthorizedForInvalidCredentials() throws Exception {
        String jsonRequest = objectMapper.writeValueAsString(invalidAuthRequest);

        mockMvc.perform(post("/api/v1/auth/login", invalidAuthRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.token").doesNotExist());
    }
}
