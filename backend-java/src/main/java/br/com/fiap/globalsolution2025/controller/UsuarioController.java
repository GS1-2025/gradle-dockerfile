package br.com.fiap.globalsolution2025.controller;

import br.com.fiap.globalsolution2025.dto.request.CadastroSensorRequest;
import br.com.fiap.globalsolution2025.entity.PulseiraRequest;
import br.com.fiap.globalsolution2025.entity.Usuario;
import br.com.fiap.globalsolution2025.repository.UsuarioRepository;
import br.com.fiap.globalsolution2025.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioRepository usuarioRepository, UsuarioService usuarioService) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        try {
            List<Usuario> usuarios = usuarioRepository.findAll();
            return ResponseEntity.ok(usuarios);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar usuários: " + e.getMessage());
        }
    }

    @PostMapping("/sensores")
    public ResponseEntity<?> cadastrarSensor(
            @RequestBody @Valid CadastroSensorRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {

        try {
            String email = userDetails.getUsername();
            PulseiraRequest sensor = usuarioService.cadastrarSensorParaUsuario(email, request);
            return ResponseEntity.ok("Sensor cadastrado com sucesso com o Id: " + sensor.getId());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Requisição inválida: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao cadastrar sensor: " + e.getMessage());
        }
    }
}
