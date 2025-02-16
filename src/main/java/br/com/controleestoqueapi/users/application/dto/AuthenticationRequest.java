package br.com.controleestoqueapi.users.application.dto;

public record AuthenticationRequest(String email, String password) {}