package com.quizter.service;

import com.quizter.service.mailsender.MailSender;
import com.quizter.util.EmailConstants;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@RequiredArgsConstructor
@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class MailWebService {

    MailSender mailSender;

    static Logger LOG = Logger.getLogger(MailWebService.class.getName());

    @Async
    public void mailSend(String recipient, String subject, String mailContent, String appUrl) {

        LOG.info("Mail sender thread = " + Thread.currentThread().getName());
        LOG.info("All the threads = " + Thread.getAllStackTraces().keySet().toString());

        Map<String, Object> model = new HashMap<>();

        model.put(EmailConstants.MAIL_CONTENT, mailContent);
        model.put("appUrl", appUrl);

        mailSender.sendMessageWithTemplate(recipient, subject, model);
    }

}