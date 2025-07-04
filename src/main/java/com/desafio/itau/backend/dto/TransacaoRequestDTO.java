package com.desafio.itau.backend.dto;

import java.time.OffsetDateTime;

public record TransacaoRequestDTO(

        Double valor,
        OffsetDateTime dataHora

) {
}
