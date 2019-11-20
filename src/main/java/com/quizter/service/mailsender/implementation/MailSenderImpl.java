package com.quizter.service.mailsender.implementation;

import com.quizter.repository.PasswordRepository;
import com.quizter.service.mailsender.MailSender;
import com.quizter.util.EmailConstants;
import com.quizter.service.mailsender.ThymeleafProcessHtml;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class MailSenderImpl implements MailSender {

    JavaMailSender javaMailSender;

    PasswordRepository passwordRepository;

    ThymeleafProcessHtml thymeleafProcessHtml;

    @Override
    public void sendMessageWithTemplate(List<String> recipientEmails, String subject, Map<String, Object> model) {
    }

    @Override
    @SneakyThrows
    public void sendMessageWithTemplate(String recipient, String subject, Map<String, Object> model) {
        MimeMessage message = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(
                message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name()
        );

        helper.setTo(recipient);

        String contentUrl = (String) model.get(EmailConstants.MAIL_CONTENT);
        String processedHtml = thymeleafProcessHtml.getProcessedHtml(Collections.EMPTY_MAP, contentUrl);

        model.put(EmailConstants.MAIL_CONTENT, processedHtml);

        String html = thymeleafProcessHtml.getProcessedHtml(model, EmailConstants.MAIL_LAYOUT);

        helper.setText(html, true);
        helper.setSubject(subject);
        helper.setFrom(EmailConstants.EMAIL_FROM);

        javaMailSender.send(message);
    }

    @Override
    public void sendMessageWithTemplate(String recipient, String subject, String model) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(recipient);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(model);
        javaMailSender.send(simpleMailMessage);
    }

}
