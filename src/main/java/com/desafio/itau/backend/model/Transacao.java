package com.desafio.itau.backend.model;

import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Transacao {

    private UUID id;
    private double valor;
    private OffsetDateTime dataHora;

}