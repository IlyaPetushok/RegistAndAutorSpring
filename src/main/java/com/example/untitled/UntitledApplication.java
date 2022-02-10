package com.example.untitled;

import com.example.untitled.email.EmailSenderService;
import com.example.untitled.email.GenerateKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

@SpringBootApplication
public class UntitledApplication {
    public static void main(String[] args) {
        SpringApplication.run(UntitledApplication.class, args);
    }

    @Autowired
    private EmailSenderService senderService;

//    @EventListener(ApplicationReadyEvent.class)
//    public void sendMail(){
//        String Key="null";
//        GenerateKey generateKey =new GenerateKey();
//        Key=generateKey.New_Key();
//
//        senderService.sendEmail("a3310010752@gmail.com",
//                "This is key",
//                Key);
//    }
}
