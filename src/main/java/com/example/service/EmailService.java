package com.example.service;

import com.example.repository.EmailRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EmailService {

    @Value("muhammadsodiqnabijonov2502@gmail.com")
    private String fromAccount;

//    @Autowired
//    private EmailRepository emailRepository;
    @Autowired
    private JavaMailSender javaMailSender;

    void sendEmailMime(String toAccount, String subject, String content) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            mimeMessage.setFrom(fromAccount);

            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(toAccount);
            helper.setSubject(subject);
            helper.setText(content);
            helper.setText(content, true);
            javaMailSender.send(mimeMessage);

//            EmailHistory emailHistory = new EmailHistory();
//            emailHistory.setEmail(toAccount);
//            emailHistory.setMessage(content);
//            emailHistory.setCreatedDate(LocalDateTime.now());

//            emailRepository.save(emailHistory);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }



}
