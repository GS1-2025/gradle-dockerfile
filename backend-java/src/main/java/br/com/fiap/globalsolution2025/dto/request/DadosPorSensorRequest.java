package br.com.fiap.globalsolution2025.dto.request;

import jakarta.validation.constraints.*;

public record DadosPorSensorRequest(
        @NotNull(message = "deviceToken obrigatorio")
        String deviceToken,
        @NotNull(message = "A temperatura é obrigatória")
        @DecimalMin(value = "-50.0", message = "Temperatura muito baixa")
        @DecimalMax(value = "100.0", message = "Temperatura muito alta")
        Double temperatura,

        @NotNull(message = "A umidade é obrigatória")
        @DecimalMin(value = "0.0", message = "Umidade mínima é 0%")
        @DecimalMax(value = "100.0", message = "Umidade máxima é 100%")
        Double umidade,

        @NotNull(message = "O índice UV é obrigatório")
        @Min(value = 0, message = "Índice UV não pode ser negativo")
        @Max(value = 5000, message = "Índice UV acima do esperado")
        Double uv

) {

}
