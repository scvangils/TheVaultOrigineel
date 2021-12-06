package com.example.thevault.controller.rest_api_controller;

import com.example.thevault.service.KlantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

@WebMvcTest
class KlantControllerTest {

    private MockMvc mockMvc;

    private KlantController klantController;
    @MockBean
    private KlantService klantService;

    @Autowired
    public KlantControllerTest(MockMvc mockMvc){
        super();
        this.mockMvc = mockMvc;
    }

    @BeforeEach
    void setUp() {
    }
    @Test
    void registreerKlantHandler(){

    }
}