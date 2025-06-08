package br.com.fiap.globalsolution2025.controller;

import br.com.fiap.globalsolution2025.dto.request.DadosPorSensorRequest;
import br.com.fiap.globalsolution2025.dto.response.DadosReponse;
import br.com.fiap.globalsolution2025.dto.request.DadosRequest;
import br.com.fiap.globalsolution2025.entity.Usuario;
import br.com.fiap.globalsolution2025.mapper.DadosMapper;
import br.com.fiap.globalsolution2025.repository.UsuarioRepository;
import br.com.fiap.globalsolution2025.service.DadosSensorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import br.com.fiap.globalsolution2025.entity.DadosSensor;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dados")
public class DadosController {

    private final DadosSensorService service;
    private final DadosMapper mapper;
    private final UsuarioRepository usuarioRepository;

    public DadosController(DadosSensorService service, DadosMapper mapper, UsuarioRepository usuarioRepository) {
        this.service = service;
        this.mapper = mapper;
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid DadosRequest request) {
        try {
            String emailUsuario = SecurityContextHolder.getContext().getAuthentication().getName();

            Usuario usuario = service.getUsuarioPorEmail(emailUsuario);
            DadosSensor saved = service.saveDataComUsuario(request, usuario);
            DadosReponse response = mapper.toResponse(saved, true);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao salvar dados: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            DadosSensor entity = service.getById(id);
            DadosReponse response = mapper.toResponse(entity, false);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Dado com ID " + id + " não encontrado.");
        }
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        try {
            List<DadosReponse> list = service.getAll().stream()
                    .map(d -> {
                        try {
                            return mapper.toResponse(d, true);
                        } catch (Exception e) {
                            throw new RuntimeException("Erro ao converter dados: " + e.getMessage());
                        }
                    })
                    .collect(Collectors.toList());

            return ResponseEntity.ok(list);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar dados: " + e.getMessage());
        }
    }

    @PostMapping("/publico")
    public ResponseEntity<?> createBySensor(@RequestBody @Valid DadosPorSensorRequest request) {
        try {
            service.saveDataComToken(request);
            return ResponseEntity.status(HttpStatus.CREATED).body("Dados enviados com sucesso.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao processar dados do sensor: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro inesperado: " + e.getMessage());
        }
    }
}
