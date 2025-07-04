package com.desafio.itau.backend.service;

import com.desafio.itau.backend.dto.EstatisticaResponseDTO;
import com.desafio.itau.backend.dto.TransacaoRequestDTO;
import com.desafio.itau.backend.exception.BusinessException;
import com.desafio.itau.backend.model.Transacao;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.*;

@Service
@Slf4j
public class TransacaoService {

    List<Transacao> transacoes = new ArrayList<>();

    public void criarTransacao(TransacaoRequestDTO request) {

        if (request.valor() == null || request.dataHora() == null) {
            log.info("Erro durante criação da transação, um ou mais campos são nulos");
            throw new BusinessException();
        } else if (request.valor() < 0 || request.dataHora().isAfter(OffsetDateTime.now())) {
            log.info("Erro durante criação da transação, o valor é negativo ou possui uma data futura");
            throw new BusinessException();
        }

        Transacao transacao = new Transacao(UUID.randomUUID(), request.valor(), request.dataHora());
        transacoes.add(transacao);
        log.info("Transação criada com sucesso");
    }

    public void deletarTransacoes() {
        transacoes.clear();
        log.info("Todas as transações foram deletadas com sucesso");
    }

    public EstatisticaResponseDTO obterEstatisticas(Long tempoEmSegundos) {
        OffsetDateTime agora = OffsetDateTime.now();
        OffsetDateTime limite = agora.minusSeconds(tempoEmSegundos);

        DoubleSummaryStatistics estatisticas = transacoes.stream()
                .filter(t -> !t.getDataHora().isBefore(limite))
                .mapToDouble(Transacao::getValor)
                .summaryStatistics();

        log.info("Obtendo estatisticas das ultimas transações");
        return EstatisticaResponseDTO.fromStats(estatisticas);
    }
}
