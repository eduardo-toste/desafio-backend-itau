package com.desafio.itau.backend.controller;

import com.desafio.itau.backend.dto.EstatisticaResponseDTO;
import com.desafio.itau.backend.dto.TransacaoRequestDTO;
import com.desafio.itau.backend.service.TransacaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class TransacaoController {

    private final TransacaoService transacaoService;

    @PostMapping("/transacao")
    public ResponseEntity<Void> criarTransacao(@RequestBody TransacaoRequestDTO request) {
        transacaoService.criarTransacao(request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/transacao")
    public ResponseEntity<Void> deletarTransacoes() {
        transacaoService.deletarTransacoes();

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/estatistica")
    public ResponseEntity<EstatisticaResponseDTO> obterEstatisticas() {
        EstatisticaResponseDTO estatisticas = transacaoService.obterEstatisticas();

        return ResponseEntity.status(HttpStatus.OK).body(estatisticas);
    }

}
