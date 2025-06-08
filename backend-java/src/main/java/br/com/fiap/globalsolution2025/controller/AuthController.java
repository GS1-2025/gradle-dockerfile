package br.com.fiap.globalsolution2025.controller;

import br.com.fiap.globalsolution2025.dto.request.AuthRequest;
import br.com.fiap.globalsolution2025.dto.request.RecoverPasswordRequest;
import br.com.fiap.globalsolution2025.dto.request.RegisterRequest;
import br.com.fiap.globalsolution2025.dto.request.ResetPasswordRequest;
import br.com.fiap.globalsolution2025.dto.response.LoginResponse;
import br.com.fiap.globalsolution2025.dto.response.MessageResponse;
import br.com.fiap.globalsolution2025.entity.Email;
import br.com.fiap.globalsolution2025.entity.Usuario;
import br.com.fiap.globalsolution2025.repository.UsuarioRepository;
import br.com.fiap.globalsolution2025.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TokenService tokenService;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthRequest authDTO) {
        try {
            var userPwd = new UsernamePasswordAuthenticationToken(
                    authDTO.email(),
                    authDTO.senha()
            );

            var auth = this.authenticationManager.authenticate(userPwd);

            var usuario = (Usuario) auth.getPrincipal();
            var token = tokenService.generateToken(usuario);

            return ResponseEntity.ok(new LoginResponse(usuario.getEmail().getEmail(), token));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao realizar login: " + e.getMessage());
        }
    }


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest registerDTO) {
        try {
            if (usuarioRepository.findByEmail_Email(registerDTO.email()).isPresent()) {
                return ResponseEntity.badRequest().body("Email já cadastrado.");
            }

            String senhaCriptografada = passwordEncoder.encode(registerDTO.senha());

            Usuario novoUsuario = new Usuario(
                    registerDTO.nome(),
                    new Email(registerDTO.email(), true),
                    senhaCriptografada,
                    registerDTO.role()
            );

            usuarioRepository.save(novoUsuario);

            return ResponseEntity.status(HttpStatus.CREATED).body("Usuário registrado com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao registrar usuário: " + e.getMessage());
        }
    }

    @PostMapping("/recover-password")
    public ResponseEntity<MessageResponse> recoverPassword(
            @RequestBody @Valid RecoverPasswordRequest request) {
        var usuarioOpt = usuarioRepository.findByEmail_Email(request.email());

        String msg = "Se o email existir, você receberá instruções para recuperação.";

        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.ok(new MessageResponse(msg));
        }

        var usuario = usuarioOpt.get();

        String token = tokenService.generateRecoverPasswordToken(usuario);

        System.out.println("Link para resetar senha: https://sensolux.com/reset-password?token=" + token);

        return ResponseEntity.ok(new MessageResponse(msg));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<MessageResponse> resetPassword(
            @RequestBody @Valid ResetPasswordRequest request) {

        String email = tokenService.validateRecoverPasswordToken(request.token());
        if (email == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new MessageResponse("Token inválido ou expirado."));
        }

        var usuarioOpt = usuarioRepository.findByEmail_Email(email);
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Usuário não encontrado."));
        }

        var usuario = usuarioOpt.get();

        String senhaCriptografada = passwordEncoder.encode(request.novaSenha());
        usuario.setSenha(senhaCriptografada);

        usuarioRepository.save(usuario);

        return ResponseEntity.ok(new MessageResponse("Senha atualizada com sucesso."));
    }



}





