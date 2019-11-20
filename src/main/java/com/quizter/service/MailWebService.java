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

    public void mailSend(String recipient, String subject, String mailContent, String appUrl) {

        Map<String, Object> model = new HashMap<>();

        model.put(EmailConstants.MAIL_CONTENT, mailContent);
        model.put("appUrl", appUrl);

        mailSender.sendMessageWithTemplate(recipient, subject, model);
    }

}