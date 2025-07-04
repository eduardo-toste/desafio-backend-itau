package com.desafio.itau.backend.controller;

import com.desafio.itau.backend.dto.EstatisticaResponseDTO;
import com.desafio.itau.backend.dto.TransacaoRequestDTO;
import com.desafio.itau.backend.service.TransacaoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.OffsetDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TransacaoController.class)
class TransacaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransacaoService transacaoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveCriarTransacaoComSucesso() throws Exception {
        TransacaoRequestDTO request = new TransacaoRequestDTO(100.0, OffsetDateTime.now());

        mockMvc.perform(post("/transacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    void deveDeletarTransacoesComSucesso() throws Exception {
        mockMvc.perform(delete("/transacao"))
                .andExpect(status().isOk());
    }

    @Test
    void deveRetornarEstatisticasComSucesso() throws Exception {
        EstatisticaResponseDTO respostaMock = new EstatisticaResponseDTO(2L, 100.0, 50.0, 100.0, 50.0);
        Mockito.when(transacaoService.obterEstatisticas(60L)).thenReturn(respostaMock);

        mockMvc.perform(get("/estatistica/60"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(2))
                .andExpect(jsonPath("$.sum").value(100.0))
                .andExpect(jsonPath("$.max").value(50.0))
                .andExpect(jsonPath("$.min").value(100.0))
                .andExpect(jsonPath("$.avg").value(50.0));
    }
}