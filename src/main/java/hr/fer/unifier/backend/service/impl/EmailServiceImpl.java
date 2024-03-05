package hr.fer.unifier.backend.service.impl;

import hr.fer.unifier.backend.UnifierProperties;
import hr.fer.unifier.backend.service.EmailService;
import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.activation.FileDataSource;
import jakarta.mail.BodyPart;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender emailSender;

    private final UnifierProperties.EmailProperties emailProperties;

    private final UnifierProperties.UnifierFrontendUrlProperties unifierFrontendUrlProperties;
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

    @Override
    public void sendRecoveryMail(String email, String token) {
        log.info(String.format("Sending recovery mail to %s", email));

        final MimeMessage message = emailSender.createMimeMessage();

        try {
            message.setFrom(emailProperties.getUsername());
            message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(email));
            message.setSubject("Oporavak lozinke");

            final MimeMultipart mimeMultipart = new MimeMultipart("related");

            final File template = new ClassPathResource("emailTemplates/passwordRecoveryTemplate.html").getFile();
            String htmlTemplate = Files.readString(template.toPath());
            String url = String.format("%s/?token=%s", unifierFrontendUrlProperties.getRecoveryUrl(), token);
            htmlTemplate = htmlTemplate.replace("{url}", url);

            final BodyPart textPart = new MimeBodyPart();
            textPart.setContent(htmlTemplate,"text/html; charset=utf-8");

            final File signatureImg = new ClassPathResource("emailTemplates/signature.png").getFile();
            final DataSource imageResource = new FileDataSource(signatureImg);
            final BodyPart imagePart = new MimeBodyPart();
            imagePart.setDataHandler(new DataHandler(imageResource));
            imagePart.setHeader("Content-ID","<signature>");
            imagePart.setDisposition(MimeBodyPart.INLINE);

            mimeMultipart.addBodyPart(textPart);
            mimeMultipart.addBodyPart(imagePart);

            message.setContent(mimeMultipart);
            emailSender.send(message);
        }catch (MessagingException msgEx){
            log.error("Couldn't send email", msgEx);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (IOException fileEx){
            log.error("Couldn't read the template", fileEx);
        }

    }
}
