package com.example.ExpenseManagement.model.email;

import org.springframework.mail.SimpleMailMessage;

public class BuilderEmailDirector {
    public static SimpleMailMessage construct (
            BuilderEmail builderEmail, String receiver,
            String subject, String text, String author
    ) {
        builderEmail.buildReceiver(receiver);
        builderEmail.buildSubject(subject);
        builderEmail.buildText(text);
        builderEmail.buildAuthor(author);
        return builderEmail.buildEmail();
    }

}
