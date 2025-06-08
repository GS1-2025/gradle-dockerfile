package br.com.fiap.globalsolution2025.security;


import br.com.fiap.globalsolution2025.repository.UsuarioRepository;
import br.com.fiap.globalsolution2025.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String uri = request.getRequestURI();
        System.out.println("[DEBUG] URI: " + uri);

        if (uri.equals("/auth/register") ||uri.equals("/auth/login") || uri.equals("/auth/reset-password")||uri.equals("/auth/recover-password") || uri.equals("/dados/publico")) {
            System.out.println("[DEBUG] URI pública, seguindo sem autenticação");
            filterChain.doFilter(request, response);
            return;
        }

        String token = recoverToken(request);
        System.out.println("[DEBUG] Token recebido: " + token);

        if (token != null) {
            String emailEndereco = tokenService.validateToken(token);
            System.out.println("[DEBUG] Email extraído do token: " + emailEndereco);

            if (emailEndereco != null) {
                var optionalUsuario = usuarioRepository.findByEmail_Email(emailEndereco);
                System.out.println("[DEBUG] Usuário encontrado? " + optionalUsuario.isPresent());

                if (optionalUsuario.isPresent()) {
                    UserDetails userDetails = optionalUsuario.get();
                    var authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    System.out.println("[DEBUG] Autenticação adicionada no SecurityContext");
                } else {
                    System.out.println("[DEBUG] Nenhum usuário encontrado com o email: " + emailEndereco);
                }
            } else {
                System.out.println("[DEBUG] Email no token é nulo (token inválido?)");
            }
        } else {
            System.out.println("[DEBUG] Token ausente ou inválido no header");
        }

        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }
        return authHeader.replace("Bearer ", "");
    }
}
