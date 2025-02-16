package br.com.controleestoqueapi.users.infrastructure.controller;

import br.com.controleestoqueapi.users.application.dto.AuthenticationRequest;
import br.com.controleestoqueapi.users.application.dto.AuthenticationResponse;
import br.com.controleestoqueapi.users.infrastructure.security.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    public AuthController(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        // 1. Autenticar o usuário.
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        // 2. Se a autenticação for bem-sucedida, carregar os detalhes do usuário.
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.email());

        // 3. Gerar o token JWT.
        String jwt = jwtService.generateToken(userDetails);

        // 4. Retornar o token JWT na resposta.
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
}