package com.quizter.service;

import com.quizter.service.mailsender.MailSender;
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

    public void mailSend(String recipient, String content, String subject, String mailUrl) {

        Map<String, Object> model = new HashMap<>();

        model.put(content, mailUrl);

        mailSender.sendMessageWithTemplate(recipient, subject, model);
    }

}