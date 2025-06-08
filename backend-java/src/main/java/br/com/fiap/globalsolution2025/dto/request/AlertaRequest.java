package br.com.fiap.globalsolution2025.dto.request;

import br.com.fiap.globalsolution2025.entity.enums.STATUS;
import br.com.fiap.globalsolution2025.entity.enums.TIPO;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AlertaRequest(
        @NotNull(message = "O tipo do alerta é obrigatório")
        TIPO tipo,

        @NotBlank(message = "A mensagem é obrigatória")
        String mensagem,

        @NotNull(message = "O nível de risco é obrigatório")
        @Min(value = 1, message = "O nível de risco deve ser no mínimo 1")
        @Max(value = 5, message = "O nível de risco deve ser no máximo 5")
        Integer nivelRisco,

        @NotNull(message = "O status é obrigatório")
        STATUS status
) {}