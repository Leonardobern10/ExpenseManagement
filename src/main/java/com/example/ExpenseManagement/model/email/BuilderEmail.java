package com.example.ExpenseManagement.model.email;

import org.springframework.mail.SimpleMailMessage;

public interface BuilderEmail {
    public void buildReceiver (String receiver);
    public void buildSubject (String subject);
    public void buildText (String text);
    public void buildAuthor (String author);
    public SimpleMailMessage buildEmail ();
}
