package com.example.backhelp.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${app.base-url:http://localhost:8080}")
    private String baseUrl;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void enviarEmailConfirmacao(String destinatario, String id) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(destinatario);
        email.setSubject("Confirme seu cadastro - Sistema HelpDesk");


        String link = baseUrl + "/api/usuarios/confirmar-email?id=" + id;
        email.setText("Olá!\n\nClique no link abaixo para confirmar seu e-mail:\n\n" + link);

        mailSender.send(email);
    }
}