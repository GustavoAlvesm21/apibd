package com.apiTP.rottenPotatoes.models;

import com.apiTP.rottenPotatoes.services.EmailService;

public class UserModel {
    private String primeiroNome;
    private String ultimoNome;
    private String email;
    private String senha;
    private String senhaCorrespondente;
    private EmailService emailService;

    public UserModel() {
    }

    public String getPrimeiroNome() {
        return primeiroNome;
    }

    public void setPrimeiroNome(String primeiroNome) {
        this.primeiroNome = primeiroNome;
    }

    public String getUltimoNome() {
        return ultimoNome;
    }

    public void setUltimoNome(String ultimoNome) {
        this.ultimoNome = ultimoNome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getSenhaCorrespondente() {
        return senhaCorrespondente;
    }

    public void setSenhaCorrespondente(String senhaCorrespondente) {
        this.senhaCorrespondente = senhaCorrespondente;
    }

    public EmailService getEmailService() {
        return emailService;
    }

    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }
}
