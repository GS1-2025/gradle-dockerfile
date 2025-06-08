package br.com.fiap.globalsolution2025.dto.response;

import org.springframework.hateoas.Link;

import java.time.LocalDateTime;
import java.util.UUID;

public record DadosReponse(
        Long id,

        Double temperatura,
        Double umidade,
        Double indiceUv,
        LocalDateTime data,
        Link link
) {
}
