package com.example.ExpenseManagement.model.email;

import org.springframework.mail.SimpleMailMessage;

public class BuilderEmailImp implements BuilderEmail {

    private final SimpleMailMessage simpleMailMessage;

    public BuilderEmailImp (SimpleMailMessage simpleMailMessage) {
        this.simpleMailMessage = simpleMailMessage;
    }

    @Override
    public void buildReceiver (String receiver) {
        simpleMailMessage.setTo(receiver);
    }

    @Override
    public void buildSubject (String categoryName) {
        String mainSubject = String.format("Alerta: Limite de Dívida Ultrapassado na Categoria %s.", categoryName);
        simpleMailMessage.setSubject(mainSubject);
    }

    @Override
    public void buildText (String text) {
        simpleMailMessage.setText(text);
    }

    @Override
    public void buildAuthor (String author) {
        simpleMailMessage.setFrom(author);
    }

    @Override
    public SimpleMailMessage buildEmail () {
        return simpleMailMessage;
    }
}
