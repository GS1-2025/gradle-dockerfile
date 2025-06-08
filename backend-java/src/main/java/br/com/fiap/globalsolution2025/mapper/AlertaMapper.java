package br.com.fiap.globalsolution2025.mapper;

import br.com.fiap.globalsolution2025.controller.DadosController;
import br.com.fiap.globalsolution2025.dto.request.AlertaRequest;
import br.com.fiap.globalsolution2025.dto.response.AlertaResponse;
import br.com.fiap.globalsolution2025.entity.Alerta;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AlertaMapper {

    public Alerta toEntity(AlertaRequest request) {
        Alerta alerta = new Alerta();
        alerta.setTipo(request.tipo());
        alerta.setMensagem(request.mensagem());
        alerta.setNivelRisco(request.nivelRisco());
        alerta.setStatus(request.status());
        return alerta;
    }

    public AlertaResponse toResponse(Alerta alerta, boolean self) throws Exception {
        Link link ;
        if (self) {
            link = linkTo(methodOn(DadosController.class).getById(alerta.getId())).withSelfRel();
        } else {
            link = linkTo(methodOn(DadosController.class).getAll()).withRel("Lista de Dados");
        }
        return new AlertaResponse(
                alerta.getId(),
                alerta.getTipo().name(),
                alerta.getMensagem(),
                alerta.getNivelRisco(),
                alerta.getStatus().name(),
                link
        );
    }
}