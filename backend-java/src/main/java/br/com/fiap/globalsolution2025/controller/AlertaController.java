package br.com.fiap.globalsolution2025.controller;

import br.com.fiap.globalsolution2025.dto.request.AlertaRequest;
import br.com.fiap.globalsolution2025.dto.response.AlertaResponse;
import br.com.fiap.globalsolution2025.entity.Alerta;
import br.com.fiap.globalsolution2025.mapper.AlertaMapper;
import br.com.fiap.globalsolution2025.service.AlertaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/alertas")
public class AlertaController {

    private final AlertaService service;
    private final AlertaMapper mapper;

    public AlertaController(AlertaService service, AlertaMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid AlertaRequest request) {
        try {
            Alerta alerta = service.save(request);
            AlertaResponse response = mapper.toResponse(alerta, true);
            URI location = URI.create("/alertas/" + alerta.getId());
            return ResponseEntity.created(location).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao criar alerta: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            Alerta alerta = service.getById(id);
            if (alerta == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Alerta não encontrado");
            }
            return ResponseEntity.ok(mapper.toResponse(alerta, false));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar alerta: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        try {
            List<AlertaResponse> list = service.getAll()
                    .stream()
                    .map(alerta -> {
                        try {
                            return mapper.toResponse(alerta, true);
                        } catch (Exception e) {
                            throw new RuntimeException("Erro ao mapear alerta: " + e.getMessage());
                        }
                    })
                    .collect(Collectors.toList());
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao listar alertas: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid AlertaRequest request) {
        try {
            Alerta alerta = service.update(id, request);
            if (alerta == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Alerta não encontrado");
            }
            return ResponseEntity.ok(mapper.toResponse(alerta, true));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao atualizar alerta: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            Alerta alerta = service.getById(id);
            if (alerta == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Alerta não encontrado");
            }
            service.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao deletar alerta: " + e.getMessage());
        }
    }
}
