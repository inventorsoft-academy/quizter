package com.quizter.service;

import com.quizter.service.mailsender.MailSender;
import com.quizter.util.EmailConstants;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class MailWebService {

    MailSender mailSender;

    public void registrationMailSend(String recipient) {
        Map<String, Object> model = new HashMap<>();

        model.put(EmailConstants.MAIL_CONTENT, EmailConstants.MAIL_CONTENT_URL);

        mailSender.sendMessageWithTemplate(recipient, EmailConstants.REGISTRATION_SUBJECT, model);
    }

}