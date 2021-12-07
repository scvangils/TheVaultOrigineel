package com.example.thevault.controller.rest_api_controller;

import com.example.thevault.domain.model.Adres;
import com.example.thevault.domain.model.Klant;
import com.example.thevault.domain.model.Rekening;
import com.example.thevault.domain.transfer.KlantDto;
import com.example.thevault.service.Facade;
import com.example.thevault.service.KlantService;
import com.example.thevault.support.hashing.BCryptWachtwoordHash;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.Mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.GsonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

@WebMvcTest
class KlantControllerTest {

    private MockMvc mockMvc;

    private KlantController klantController;
    @MockBean
    private KlantService klantService;
    @MockBean
    private Facade facade;

    @Autowired
    public KlantControllerTest(MockMvc mockMvc){
        super();
        this.mockMvc = mockMvc;
    }

    @BeforeEach
    void setUp() {

    }
    @Test
    void registreerKlantHandler() throws JsonProcessingException {
        String gehashtWachtwoord = BCryptWachtwoordHash.hashWachtwoord("testWW");
        Adres adres = new Adres("straat", 357, "C", "1000AA", "Amsterdam");
        Klant testKlant = new Klant("testKlant", gehashtWachtwoord,
                null, null, "Jan", adres, 145801354, LocalDate.of(1975, 7, 30));
        Klant fouteKlant = new Klant("testKlant", gehashtWachtwoord,
                null, null, "Jan", adres, 145801354, LocalDate.of(1975, 7, 30));
        testKlant.setGebruikerId(2);
        Rekening rekening = new Rekening("NL20INGB0006582287", 1000);
        ObjectMapper objectMapper = new ObjectMapper();
        String testKlantJson = objectMapper.writeValueAsString(testKlant);
        testKlant.setRekening(rekening);
        KlantDto testKlantDto = new KlantDto(testKlant);
        Mockito.when(facade.registreerKlant(testKlant)).thenReturn(testKlantDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/register");
        requestBuilder.content(testKlantJson).contentType(MediaType.APPLICATION_JSON);
        try {
            ResultActions response = mockMvc.perform(requestBuilder);
            response.andExpect(MockMvcResultMatchers.status().isCreated()).andExpect(MockMvcResultMatchers
                            .content()
                            .json("{\"naam\":\"Jan\"," +
                            "\"gebruikersnaam\":\"testKlant\",\"iban\":\"NL20INGB0006582287\"," +
                                    "\"postcodeEnHuisnummer\":\"1000AA / 357C\"}"))
                    .andDo(MockMvcResultHandlers.print());
        } catch (Exception e){
            System.out.println("nee fout: " + e);
        }
    }
    /*        String echteKlant = String.format("{\"gebruikerId\":2,\"gebruikersnaam\":\"testKlant\",\"" +
                "wachtwoord\":\"%s\",\"naam\":\"Jan\",\"adres\":{" +
                "\"straatnaam\":\"straat\"," +
                "\"huisnummer\":357," +
                "\"toevoeging\":\"C\"," +
                "\"postcode\":\"1000AA\"," +
                "\"plaatsnaam\":\"Amsterdam\"" +
                "},\"bsn\":145801354,\"rekening\":null,\"geboortedatum\":\"1975-07-30\"}", gehashtWachtwoord);
        assertEquals(testKlantJson, echteKlant);*/
}