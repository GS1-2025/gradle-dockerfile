package br.com.fiap.globalsolution2025.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;

public record DadosRequest(
        @Min(value = -40)
        @Max(value = 60)
        Double temperatura,

        Double umidade,
        @Positive
        @Min(0)
        @Max(11)
        Double indiceUv) {
}
