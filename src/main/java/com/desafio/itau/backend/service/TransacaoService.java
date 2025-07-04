package com.desafio.itau.backend.service;

import com.desafio.itau.backend.dto.TransacaoRequestDTO;
import com.desafio.itau.backend.model.Transacao;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class TransacaoService {

    List<Transacao> transacoes = new ArrayList<>();

    public void criarTransacao(TransacaoRequestDTO request) {
        Transacao transacao = new Transacao(UUID.randomUUID(), request.valor(), request.dataHora());
        transacoes.add(transacao);
    }

    public void deletarTransacoes() {
        transacoes.clear();
    }

}
