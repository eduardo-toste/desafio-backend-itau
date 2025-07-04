package com.desafio.itau.backend.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Value;

import java.time.OffsetDateTime;

public record TransacaoRequestDTO(

        @NotNull
        @PositiveOrZero
        double valor,

        @NotNull
        @PastOrPresent
        OffsetDateTime dataHora

) {
}
