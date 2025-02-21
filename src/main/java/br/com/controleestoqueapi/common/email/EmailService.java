package br.com.controleestoqueapi.common.email;

public interface EmailService {
    void sendEmail(String to, String subject, String text);
}