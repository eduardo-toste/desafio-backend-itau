package com.desafio.itau.backend.service;

import com.desafio.itau.backend.dto.EstatisticaResponseDTO;
import com.desafio.itau.backend.dto.TransacaoRequestDTO;
import com.desafio.itau.backend.exception.BusinessException;
import com.desafio.itau.backend.model.Transacao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransacaoServiceTest {

    @InjectMocks
    private TransacaoService transacaoService;

    @Test
    void deveCriarTransacaoValida() {
        TransacaoRequestDTO request = new TransacaoRequestDTO(100.0, OffsetDateTime.now());

        transacaoService.criarTransacao(request);

        EstatisticaResponseDTO estatisticas = transacaoService.obterEstatisticas(60L);
        assertEquals(1, estatisticas.count());
        assertEquals(100.0, estatisticas.sum());
        assertEquals(100.0, estatisticas.avg());
        assertEquals(100.0, estatisticas.min());
        assertEquals(100.0, estatisticas.max());
    }

    @Test
    void naoDeveCriarTransacaoComValorNulo() {
        TransacaoRequestDTO request = new TransacaoRequestDTO(null, OffsetDateTime.now());

        assertThrows(BusinessException.class, () -> transacaoService.criarTransacao(request));

        EstatisticaResponseDTO stats = transacaoService.obterEstatisticas(60L);
        assertEquals(0, stats.count());
    }

    @Test
    void naoDeveCriarTransacaoComDataNula() {
        TransacaoRequestDTO request = new TransacaoRequestDTO(100.0, null);

        assertThrows(BusinessException.class, () -> transacaoService.criarTransacao(request));

        EstatisticaResponseDTO stats = transacaoService.obterEstatisticas(60L);
        assertEquals(0, stats.count());
    }

    @Test
    void naoDeveCriarTransacaoComValorNegativo() {
        TransacaoRequestDTO request = new TransacaoRequestDTO(-100.0, OffsetDateTime.now());

        assertThrows(BusinessException.class, () -> transacaoService.criarTransacao(request));

        EstatisticaResponseDTO stats = transacaoService.obterEstatisticas(10L);
        assertEquals(0, stats.count());
    }

    @Test
    void naoDeveCriarTransacaoComDataInvalida() {
        TransacaoRequestDTO request = new TransacaoRequestDTO(
                100.0,
                OffsetDateTime.of(2025, 10, 2, 14, 20, 22, 0, ZoneOffset.UTC)
        );

        assertThrows(BusinessException.class, () -> transacaoService.criarTransacao(request));

        EstatisticaResponseDTO stats = transacaoService.obterEstatisticas(10L);
        assertEquals(0, stats.count());
    }

    @Test
    void deveDeletarTodasAsTransacoes() {
        transacaoService.criarTransacao(new TransacaoRequestDTO(100.0, OffsetDateTime.now()));
        transacaoService.criarTransacao(new TransacaoRequestDTO(50.0, OffsetDateTime.now()));

        EstatisticaResponseDTO estatisticasAntes = transacaoService.obterEstatisticas(60L);
        assertEquals(2, estatisticasAntes.count());

        transacaoService.deletarTransacoes();

        EstatisticaResponseDTO estatisticasDepois = transacaoService.obterEstatisticas(60L);
        assertEquals(0, estatisticasDepois.count());
        assertEquals(0.0, estatisticasDepois.sum());
    }

    @Test
    void deveObterAsEstatisticas() {
        transacaoService.criarTransacao(new TransacaoRequestDTO(100.0, OffsetDateTime.now()));
        transacaoService.criarTransacao(new TransacaoRequestDTO(100.0, OffsetDateTime.now()));

        EstatisticaResponseDTO estatisticas = transacaoService.obterEstatisticas(60L);

        assertEquals(2, estatisticas.count());
        assertEquals(200.0, estatisticas.sum());
        assertEquals(100.0, estatisticas.avg());
        assertEquals(100.0, estatisticas.min());
        assertEquals(100.0, estatisticas.max());
    }

    @Test
    void deveRetornarEstatisticasZeradasQuandoNaoHaTransacoes() {
        EstatisticaResponseDTO stats = transacaoService.obterEstatisticas(60L);

        assertEquals(0, stats.count());
        assertEquals(0.0, stats.sum());
        assertEquals(0.0, stats.avg());
        assertEquals(0.0, stats.min());
        assertEquals(0.0, stats.max());
    }

    @Test
    void deveIgnorarTransacoesForaDoIntervalo() {
        OffsetDateTime agora = OffsetDateTime.now();
        OffsetDateTime antiga = agora.minusSeconds(120);

        transacaoService.criarTransacao(new TransacaoRequestDTO(100.0, antiga));
        transacaoService.criarTransacao(new TransacaoRequestDTO(200.0, agora));

        EstatisticaResponseDTO stats = transacaoService.obterEstatisticas(60L);

        assertEquals(1, stats.count());
        assertEquals(200.0, stats.sum());
    }

}