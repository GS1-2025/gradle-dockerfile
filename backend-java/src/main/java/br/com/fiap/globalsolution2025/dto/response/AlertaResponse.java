package br.com.fiap.globalsolution2025.dto.response;


import org.springframework.hateoas.Link;

import java.util.UUID;

public record AlertaResponse(
        Long id,
        String tipo,
        String mensagem,
        Integer nivelRisco,
        String status,
        Link link
) {}