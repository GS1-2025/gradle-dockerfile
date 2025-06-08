package br.com.fiap.globalsolution2025.dto.request;

import jakarta.validation.constraints.NotBlank;

public record BetweenRequest(
        @NotBlank(message = "A data inicial é obrigatória")
        String startStr,

        @NotBlank(message = "A data final é obrigatória")
        String endStr
) {
}
