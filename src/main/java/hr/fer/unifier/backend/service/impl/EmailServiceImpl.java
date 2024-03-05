package hr.fer.unifier.backend.service.impl;

import hr.fer.unifier.backend.UnifierProperties;
import hr.fer.unifier.backend.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender emailSender;

    private final UnifierProperties.EmailProperties emailProperties;
    @Override
    public void testEmail() {
        log.info("Into email service.");

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(emailProperties.getUsername());
        simpleMailMessage.setTo("deantrkulja2001@gmail.com");
        simpleMailMessage.setSubject("Ovo je test!");
        simpleMailMessage.setText("Poštovani, \novo je testna poruka.\nS poštovanjem, Vaš Unifier");

        emailSender.send(simpleMailMessage);
        log.info("Email sent!!");

    }
}
