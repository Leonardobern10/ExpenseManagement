package com.example.ExpenseManagement.model.movimentations;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Document(collection = "movimentations")
public class Movimentations {

    @Id
    private String id;
    private String userId;
    private String description;
    private double amount;
    private String categoryName;
    private LocalDateTime date = LocalDateTime.now();
    private String person;
    private List<String> registers;


    public Movimentations(String userId, String description, double amount,
                          String categoryName, LocalDateTime date) {
        this.userId = userId;
        this.description = description;
        this.amount = amount;
        this.categoryName = categoryName;
        this.date = date;
        this.person = null;
        this.registers = null;
    }

    public Movimentations(String userId, String description, double amount,
                          String categoryName, LocalDateTime date, String person) {
        this.userId = userId;
        this.description = description;
        this.amount = amount;
        this.categoryName = categoryName;
        this.date = date;
        this.person = person;
        this.registers = null;
    }

    public Movimentations(String userId, String description, double amount,
                          String categoryName, LocalDateTime date, String person,
                          List<String> registers) {
        this.userId = userId;
        this.description = description;
        this.amount = amount;
        this.categoryName = categoryName;
        this.date = date;
        this.person = person;
        this.registers = registers;
    }

    public Movimentations() {}

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCategoryName () {
        return categoryName;
    }

    public void setCategoryName (String categoryName) {
        this.categoryName = categoryName;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getPerson () {
        return person;
    }

    public void setPerson (String person) {
        this.person = person;
    }

    public List<String> getRegisters () {
        return registers;
    }

    public void setRegisters (List<String> registers) {
        this.registers = registers;
    }

    public void addRegister (String register) {
        this.registers.add(register);
    }
}
