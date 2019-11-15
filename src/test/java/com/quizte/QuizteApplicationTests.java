package com.quizte;

import com.quizte.service.MailWebService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class QuizteApplicationTests {

    @Autowired
    MailWebService mailWebService;

    @Test
    void contextLoads() {
        mailWebService.registrationMailSend("trozzato@gmail.com");
    }

}
