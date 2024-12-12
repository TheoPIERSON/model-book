package com.masphoto.masphoto.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
@Service
public class NotificationService {

    private final JavaMailSender javaMailSender;

    @Autowired
    public NotificationService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendNotification(Validation validation){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(validation.getUser().getEmail());
        message.setSubject("Votre code d'activation Onyx Institut");
        String text =  String.format(
                "Bonjour %s,\n\n"+
                        "Merci d'avoir créé un compte chez Onyx Institut\n\n"+
                        "Voici le code d'activation de votre compte:\n\n %s",
                validation.getUser().getUsername(),
                validation.getCode()
        );
        message.setText(text);
        javaMailSender.send(message);
    }


    public void sendResetPasswordEmail(String email, String resetToken) {
        String resetLink = "http://localhost:4200/password-reset/" + resetToken;
        String message = String.format(
                """
                Bonjour,
                
                Vous avez demandé à réinitialiser votre mot de passe. Cliquez sur le lien suivant pour procéder :
                %s
                
                Ce lien expirera dans 15 minutes.
                """,
                resetLink
        );
        SimpleMailMessage emailMessage = new SimpleMailMessage();
        emailMessage.setTo(email);
        emailMessage.setSubject("Réinitialisation du mot de passe");
        emailMessage.setText(message);

        javaMailSender.send(emailMessage);
    }


}
