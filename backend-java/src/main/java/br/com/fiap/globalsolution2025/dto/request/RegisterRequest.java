package br.com.fiap.globalsolution2025.dto.request;


import br.com.fiap.globalsolution2025.entity.enums.USER_ROLE;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterRequest(
        @NotBlank(message = "O nome não pode estar em branco")
        String nome,

        @NotBlank(message = "O e-mail não pode estar em branco")
        @Email(message = "Formato de e-mail inválido")
        String email,

        @NotBlank(message = "A senha não pode estar em branco")
        String senha,

        @NotNull(message = "A função (role) é obrigatória")
        USER_ROLE role) {}
