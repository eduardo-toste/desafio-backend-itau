package com.desafio.itau.backend.service;

import com.desafio.itau.backend.dto.EstatisticaResponseDTO;
import com.desafio.itau.backend.dto.TransacaoRequestDTO;
import com.desafio.itau.backend.exception.BusinessException;
import com.desafio.itau.backend.model.Transacao;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.*;

@Service
public class TransacaoService {

    List<Transacao> transacoes = new ArrayList<>();

    public void criarTransacao(TransacaoRequestDTO request) {

        if (request.valor() == null || request.dataHora() == null) {
            throw new BusinessException();
        } else if (request.valor() < 0 || request.dataHora().isAfter(OffsetDateTime.now())) {
            throw new BusinessException();
        }

        Transacao transacao = new Transacao(UUID.randomUUID(), request.valor(), request.dataHora());
        transacoes.add(transacao);
    }

    public void deletarTransacoes() {
        transacoes.clear();
    }

    public EstatisticaResponseDTO obterEstatisticas(Long tempoEmSegundos) {
        OffsetDateTime agora = OffsetDateTime.now();
        OffsetDateTime limite = agora.minusSeconds(tempoEmSegundos);

        DoubleSummaryStatistics estatisticas = transacoes.stream()
                .filter(t -> !t.getDataHora().isBefore(limite))
                .mapToDouble(Transacao::getValor)
                .summaryStatistics();

        return EstatisticaResponseDTO.fromStats(estatisticas);
    }
}
