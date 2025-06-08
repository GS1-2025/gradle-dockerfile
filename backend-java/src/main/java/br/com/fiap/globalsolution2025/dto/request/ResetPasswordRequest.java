package br.com.fiap.globalsolution2025.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ResetPasswordRequest(
        @NotBlank(message = "O token é obrigatório")
        String token,

        @NotBlank(message = "A nova senha é obrigatória")
        @Size(min = 6, message = "A nova senha deve ter pelo menos 6 caracteres")
        String novaSenha
) {}
