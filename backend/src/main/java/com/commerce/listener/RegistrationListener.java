package com.commerce.listener;

import com.commerce.constants.MailConstants;
import com.commerce.model.event.OnRegistrationCompleteEvent;

import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class RegistrationListener {

    private final JavaMailSender mailSender;
    private final MailConstants mailConstants;

    @EventListener
    public void confirmRegistration(OnRegistrationCompleteEvent event) {

        String recipientAddress = event.getUser().getEmail();
        String subject = "\uD83D\uDD11 SightWare Registration Confirmation";
        String confirmationUrl = mailConstants.getHostAddress() + "/registrationConfirm?token=" + event.getToken();
        String message = "Hi ,\n\nPlease confirm your email with this link. ";

        SimpleMailMessage email = createMail(recipientAddress, subject, confirmationUrl, message);
        mailSender.send(email);

    }

    private SimpleMailMessage createMail(String recipientAddress, String subject, String confirmationUrl,
            String message) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message + "\n\n" + confirmationUrl + "\n\n\nw/ SightWare Team");
        return email;
    }

}
