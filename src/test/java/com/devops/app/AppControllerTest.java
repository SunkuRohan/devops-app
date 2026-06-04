package com.devops.app;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AppControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void healthEndpointReturnsOk() throws Exception {
        mockMvc.perform(get("/api/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("ok"))
                .andExpect(jsonPath("$.version").exists())
                .andExpect(jsonPath("$.environment").exists());
    }

    @Test
    void infoEndpointReturnsAppDetails() throws Exception {
        mockMvc.perform(get("/api/info"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.appName").value("devops-app"))
                .andExpect(jsonPath("$.version").exists());
    }

    @Test
    void greetEndpointReturnsDefaultGreeting() throws Exception {
        mockMvc.perform(get("/api/greet"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Hello, World!"));
    }

    @Test
    void greetEndpointReturnsCustomName() throws Exception {
        mockMvc.perform(get("/api/greet?name=Rohan"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Hello, Rohan!"));
    }
}
