package com.quizter.service.mailsender;

import com.quizter.entity.User;

import java.util.List;
import java.util.Map;

public interface MailSender {

    void sendMessageWithTemplate(List<String> recipientEmails, String subject, Map<String, Object> model);

    void sendMessageWithTemplate(String recipient, String subject, Map<String, Object> model);

    void sendMessageWithTemplate(String recipient, String subject, String model);

}