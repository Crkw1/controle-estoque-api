package br.com.controleestoqueapi.users.infrastructure.controller;

import br.com.controleestoqueapi.users.application.dto.AuthenticationRequest;
import br.com.controleestoqueapi.users.infrastructure.security.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false) // Desabilita os filtros
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthenticationManager authenticationManager;

    @MockitoBean
    private UserDetailsService userDetailsService;

    @MockitoBean
    private JwtService jwtService;

    @Autowired
    private ObjectMapper objectMapper;

    private AuthenticationRequest authRequest;

    @BeforeEach
    void setUp() {
        authRequest = new AuthenticationRequest("test@example.com", "password");
    }

    @Test
    void login_WithValidCredentials_ShouldReturnToken() throws Exception {
        // Arrange
        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                .username("test@example.com")
                .password("hashedPassword")
                .roles("USER")
                .build();

        when(userDetailsService.loadUserByUsername(authRequest.email())).thenReturn(userDetails);
        when(jwtService.generateToken(userDetails)).thenReturn("test.jwt.token");

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);

        // Act & Assert
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.token").value("test.jwt.token"));

        verify(userDetailsService).loadUserByUsername(authRequest.email());
        verify(jwtService).generateToken(userDetails);
        verify(authenticationManager).authenticate(any());
    }
}