package com.apiTP.rottenPotatoes.event.listener;

import com.apiTP.rottenPotatoes.entity.User;
import com.apiTP.rottenPotatoes.event.RegistrationCompleteEvent;
import com.apiTP.rottenPotatoes.services.EmailSender;
import com.apiTP.rottenPotatoes.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {


    @Autowired
    private UserService userService;

    @Autowired
    private EmailSender emailSender;
    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        // Criando o token de verificação para o usuário com o link
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        userService.salvarTokenDeVerificacao(token, user);

        // Mandando o email para o usuário
        String url = event.getApplicationUrl() + "/verifyRegistration?token=" + token;

        emailSender.send(user.getEmail(), url);
        //enviaVerificacaoPorEmail()
       // log.info("Clique no link para verificar sua conta: {}", url);

    }
}
